package edu.ucf.flappydragon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

enum ObType {
    oneWay, falling, regular, wall;
}

public class Movers {

    Sprite sprite;
    public Rectangle hitbox;
    public ObType type;

    public Movers(int x, int y, int width, int height, String img, ObType ot) {
        sprite = new Sprite(new Texture(img));
        hitbox = new Rectangle(x, y, width,height);
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        type = ot;
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public void update() {
        hitbox.x -= 7;
        sprite.setPosition(hitbox.x, hitbox.y);

        if (type == ObType.falling) {
            hitbox.y -= 7;
        }
    }

}
