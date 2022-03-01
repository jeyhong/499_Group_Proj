package scenes;

import NMM.Camera;
import NMM.GameObj;
import renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class SceneInit {

    protected Renderer renderer =  new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObj> gameObjs = new ArrayList<>();

    public SceneInit(){

    }

    public void init(){

    }

    public void start(){
        for(GameObj go: gameObjs){
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObjToScene(GameObj go){
        if(!isRunning){
            gameObjs.add(go);
        }else{
            gameObjs.add(go);
            go.start();
            this.renderer.add(go);
        }
    }

    public abstract void update(float dt);

    public Camera camera(){
        return this.camera;
    }
}
