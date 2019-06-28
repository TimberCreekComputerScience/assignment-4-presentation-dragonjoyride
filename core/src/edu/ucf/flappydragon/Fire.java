package edu.ucf.flappydragon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Fire {
    private Sprite sprite;
    private Rectangle hitBox;
    public int y;
    private int x;
    private Vector2 position;
    private Vector2 velocity;
    private float height = 100f;

    public Fire(int x, int y, float push) {
        int width = 100;
        this.sprite = new Sprite(new Texture("firepic.png"));
        this.x = x;
        this.y = y;
        this.hitBox = new Rectangle((float)x, (float)y, (float)width, this.height);
        this.position = new Vector2((float)x, (float)y);
        this.velocity = new Vector2(push, 0.0F);
        sprite.setPosition(x,y);
        this.sprite.setSize((float)width, this.height);
    }

    public void update(float deltaTime) {
        position.x -= this.velocity.x * deltaTime;
        this.sprite.setPosition(this.position.x, this.position.y);
        this.hitBox.x = this.position.x;
        this.hitBox.y = this.position.y;
    }

    public void draw(SpriteBatch b) {
        this.sprite.draw(b);
    }

    public Rectangle getHitBox() {
        return this.hitBox;
    }
}
