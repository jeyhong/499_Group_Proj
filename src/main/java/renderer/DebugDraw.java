package renderer;

import NMM.Window;
import Utils.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DebugDraw {
    private static int MAX_LINES = 500;

    private static List<Line2D> lines = new ArrayList<>();
    private static float[] vertexArray = new float[MAX_LINES * 6 * 2];
    private static Shader shader = AssetPool.getShader("assets/shaders/debugLine.glsl");

    private static int vaoID;
    private static int vboID;

    private static boolean started = false;

    public static void start(){
        //Generate vao
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        //Create vbo and buffer memory
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        //Enable vertex array attribs
        glVertexAttribPointer(0,3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(0,3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        //TODO SET LINE WIDTH
    }

    public static void beginFrame(){
        if(!started){
            start();;
            started = true;
        }

        //Remove dead lines
        for(int i = 0; i< lines.size(); i++){
            if(lines.get(i).beginFrame() < 0){
                lines.remove(i);
                i--;
            }
        }
    }

    public static void draw(){
        if(lines.size() <= 0) return;

        int index = 0;
        for(Line2D line : lines){
            for(int i = 0; i < 2; i++){
                Vector2f position = i == 0 ? line.getStart() : line.getEnd();
                Vector3f color = line.getColor();

                vertexArray[index] = position.x;
                vertexArray[index + 1] = position.y;
                vertexArray[index + 2] = -10.f;

                vertexArray[index + 3] = color.x;
                vertexArray[index + 4] = color.y;
                vertexArray[index + 5] = color.z;
                index += 6;
            }
        }
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0, lines.size() * 6 * 2));

        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawArrays(GL_LINES, 0, lines.size() * 6 * 2);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shader.detach();
    }

    public static void addLine2D(Vector2f start, Vector2f end){
        addLine2D(start, end, new Vector3f(0,1,0) , 1);
    }

    public static void addLine2D(Vector2f start, Vector2f end, Vector3f color){
        addLine2D(start, end, color, 1);
    }

    public static void addLine2D(Vector2f start, Vector2f end, Vector3f color, int lifetime){
        if(lines.size() >= MAX_LINES)  return;
        DebugDraw.lines.add(new Line2D(start, end, color, lifetime));
    }


}