package components;

import NMM.GameObj;

public abstract class Component {

    public GameObj gameObj =  null;

    public void start(){

    }

    public abstract void update(float dt);

}
