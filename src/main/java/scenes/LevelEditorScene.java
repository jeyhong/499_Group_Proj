package scenes;

import NMM.Camera;
import NMM.GameObj;
import NMM.Prefabs;
import NMM.Transform;
import Utils.AssetPool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.source.tree.AssertTree;
import components.MouseControls;
import components.Sprite;
import components.SpriteRenderer;
import components.SpriteSheet;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderer.DebugDraw;

public class LevelEditorScene extends SceneInit {

    private SpriteSheet sprites;

    MouseControls mc = new MouseControls();

    public LevelEditorScene(){

    }

    @Override
    public void init(){
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpritesheet("assets/images/Tiles/tiles_spritesheet.png");

     //   DebugDraw.addLine2D(new Vector2f(0 ,0), new Vector2f(800, 800), new Vector3f(1, 0, 0), 120);
        if(levelLoaded){
            this.activeGameObj = gameObjs.get(0);

            return;
        }
        //TODO causing bugs; need to fix
//        GameObj obj1 = new GameObj("Obj1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), 4);
//        SpriteRenderer obj1SprRenderer = new SpriteRenderer();
//        Sprite obj1Spr = new Sprite();
//        obj1Spr.setTexture(AssetPool.getTexture("assets/images/blendImage1.png"));
//        obj1SprRenderer.setSprite(obj1Spr);
//        obj1.addComponent(obj1SprRenderer);
//        this.addGameObjToScene(obj1);
//        this.activeGameObj = obj1;
//
//        GameObj obj2 = new GameObj("Obj2", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
//        SpriteRenderer obj2SprRenderer = new SpriteRenderer();
//        Sprite obj2Spr = new Sprite();
//        obj2Spr.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
//        obj2SprRenderer.setSprite(obj2Spr);
//        obj2.addComponent(obj2SprRenderer);
//        this.addGameObjToScene(obj2);
    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/Tiles/tiles_spritesheet.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/Tiles/tiles_spritesheet.png"),
                        16, 16, 172, 0));

    }

    @Override
    public void update(float dt) {
        mc.update(dt);

        for(GameObj go : this.gameObjs){
            go.update(dt);
        }
        this.renderer.render();
    }

    @Override
    public void imgui(){
        ImGui.begin("Test");

        ImVec2 windowsPos = new ImVec2();
        ImGui.getWindowPos(windowsPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowsPos.x + windowSize.x;
        for(int i = 0; i< sprites.size(); i++){
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if(ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)){
                GameObj obj = Prefabs.generateSprObj(sprite, spriteWidth, spriteHeight);
                mc.pickUp(obj);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if(i + 1 < sprites.size() && nextButtonX2 < windowX2){
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
