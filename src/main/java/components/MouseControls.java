package components;

import NMM.GameObj;
import NMM.MouseListener;
import NMM.Window;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControls extends Component{

    GameObj heldObj = null;

    public void pickUp(GameObj go){
        this.heldObj = go;
        Window.getScene().addGameObjToScene(go);
    }

    public void place(){
        this.heldObj = null;
    }

    @Override
    public void update(float dt){
        if(heldObj != null){
            heldObj.transform.position.x = MouseListener.getOrthoX() -16;
            heldObj.transform.position.y = MouseListener.getOrthoY() -16;

            if(MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
                place();
            }
        }
    }
}
