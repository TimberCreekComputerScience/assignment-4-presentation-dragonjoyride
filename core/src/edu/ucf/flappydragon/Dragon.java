package edu.ucf.flappydragon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Dragon {

    Sprite sprite;
    public Rectangle hitbox;
    public Rectangle breathBox;
    int width = 150;
    int height = 100;
    int x;
    int y;

    float velocity;

    float time = 0;

    Texture downText = new Texture("ogDragDown.png");
    Texture upText = new Texture("ogDragUp.png");

    public Dragon(int x, int y) {
        this.x=x;
        this.y=y;
        sprite = new Sprite(downText);
        hitbox = new Rectangle(x, y, width,height);
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public void update(float deltaTime) {
        time += deltaTime;
        velocity += -1000 * deltaTime;

        hitbox.y += velocity * deltaTime;

        if (hitbox.y < 0) {
            hitbox.y = 0;
            velocity *= -0.75;
        }

        sprite.setPosition(hitbox.x, hitbox.y);

        if (time > 0.2) {
            if (sprite.getTexture() == downText) {
                sprite.setTexture(upText);
            } else {
                sprite.setTexture(downText);
            }
            time = 0;
        }


    }

    public void jump() {
        velocity = 500;
    }

}
