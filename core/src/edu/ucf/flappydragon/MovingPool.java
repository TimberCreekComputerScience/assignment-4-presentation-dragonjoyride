package edu.ucf.flappydragon;

import com.badlogic.gdx.utils.Pool;

public class MovingPool extends Pool<Movers> {

    @Override
    protected Movers newObject() {
        Movers temp = new Movers(0,0, 0, 0, "", ObType.regular);
        return temp;
    }

}

