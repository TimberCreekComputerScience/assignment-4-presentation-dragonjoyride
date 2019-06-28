package edu.ucf.flappydragon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MovingBackground {

    public static Texture img;
    public static Sprite background;
    public static Texture img2;
    public static Sprite background2;
    Vector2 position;
    Vector2 position2;
    Vector2 velocity;
    Vector2 velocity2;

    public MovingBackground(float push) {
        img = new Texture("clouds1.jpg");
        background = new Sprite (img);
        background.setPosition(0,0);
        background.setSize(GameController.width + 20, GameController.height);

        img2 = new Texture("clouds2.jpg");
        background2 = new Sprite (img2);
        background2.setPosition(GameController.width,0);
        background2.setSize(GameController.width + 20, GameController.height + 20);

        position = new Vector2(0, 0);
        position2 = new Vector2(GameController.width, 0);
        velocity = new Vector2(push, 0.0F);
        velocity2 = new Vector2(push, 0.0F);
    }

    public void update(float deltaTime) {
        position.x -= this.velocity.x * deltaTime;
        position2.x -= this.velocity.x * deltaTime;
        background.setPosition(this.position.x, this.position.y);
        background2.setPosition(this.position2.x, this.position2.y);
        if(background.getX() < -GameController.width){
            position.x = GameController.width;
            background.setPosition(GameController.width, 0);
        }
        if(background2.getX() < -GameController.width){
            position2.x = GameController.width;

            background2.setPosition(GameController.width, 0);
        }
    }

    public static void draw(SpriteBatch b) {
        background.draw(b);
        background2.draw(b);
    }


}
