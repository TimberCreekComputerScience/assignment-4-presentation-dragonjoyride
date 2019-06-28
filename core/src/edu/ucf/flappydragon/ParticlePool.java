package edu.ucf.flappydragon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Pool;

class ParticlePool extends Pool<ParticleEffect> {
    TextureAtlas particleAtlas;

    @Override
    protected ParticleEffect newObject() {
        ParticleEffect temp = new ParticleEffect();
        temp.load(Gdx.files.internal("breathe.p"), Gdx.files.internal(""));
        return temp;
    }
}
