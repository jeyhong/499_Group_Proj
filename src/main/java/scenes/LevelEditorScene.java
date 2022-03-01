package scenes;

import NMM.Camera;
import NMM.GameObj;
import NMM.Transform;
import Utils.AssetPool;
import components.SpriteRenderer;
import org.joml.Vector2f;

public class LevelEditorScene extends SceneInit {

    public LevelEditorScene(){

    }

    @Override
    public void init(){
        this.camera = new Camera(new Vector2f(-250, 0));

        GameObj obj1 = new GameObj("Obj1", new Transform(new Vector2f(100, 100), new Vector2f(64, 64)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/p1_front.png")));
        this.addGameObjToScene(obj1);

        GameObj obj2 = new GameObj("Obj2", new Transform(new Vector2f(200, 100), new Vector2f(64, 64)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/p3_front.png")));
        this.addGameObjToScene(obj2);

        GameObj obj3 = new GameObj(("Obj3"), new Transform(new Vector2f(300, 100), new Vector2f(64, 64)));
        obj3.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/buttonGreen_pressed.png")));
        this.addGameObjToScene(obj3);

        loadResources();
    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");

    }
    @Override
    public void update(float dt) {
        for(GameObj go : this.gameObjs){
            go.update(dt);
        }
        this.renderer.render();
    }
}
