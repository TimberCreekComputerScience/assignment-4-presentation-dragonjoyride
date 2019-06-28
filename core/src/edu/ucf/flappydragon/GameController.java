package edu.ucf.flappydragon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	public static int width = 800;
	public static int height = 800;
	Random r;
	MovingBackground bg;

	boolean[] isBlocked;

	Dragon dragon;
	ArrayList<Movers> obstacles;
	ArrayList<Movers> fires;
	ArrayList<Coin> coins;
	ArrayList<Fire> fyes;
	//added all these variables
	int scoreS = 0;
	int coinsC = 0;
	String gameCoins;
	BitmapFont coinsBitmap;
	String hitCoin = "+ 5";
	String gameScore;
	BitmapFont scoreBitmap;
	boolean timerjustStarted = false;
	float coinTime = 0;
float timer = 0;
float timer2 = 0;
	float time = 0;
	int[] changerArray = {-1, 1};

	ArrayList<ParticleEffect> fire;
	ParticlePool pool;

	@Override
	public void create () {
		batch = new SpriteBatch();
		r = new Random();
		coinsBitmap = new BitmapFont();

		bg = new MovingBackground(100);

		obstacles = new ArrayList<Movers>();
		fires = new ArrayList<Movers>();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		isBlocked = new boolean[20];

		dragon = new Dragon(100, height / 2);
coins = new ArrayList<Coin>();
fyes = new ArrayList<Fire>();
		fire = new ArrayList<ParticleEffect>();
		pool = new ParticlePool();
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		time += Gdx.graphics.getDeltaTime();
		timer+= Gdx.graphics.getDeltaTime();
		timer2+= Gdx.graphics.getDeltaTime();
		if(timer>=4){
			coins.add(new Coin((900), (int) (100 + Math.random() * 700), 420));
			timer-=4;

		}
		if(timer2>=6){
			fyes.add(new Fire( (900), (int) (100 + Math.random() * 700), 420));
			timer2-=6;
		}
		bg.draw(batch);
		dragon.draw(batch);

		for (Movers s : obstacles) {
			s.draw(batch);
		}

		for (Movers f : fires) {
			f.draw(batch);
		}

		for (ParticleEffect f : fire) {
			f.draw(batch);
		}
		for(Coin c: coins){
			c.draw(batch);
		}
		for(Fire f: fyes){
			f.draw(batch);
		}
		scoreS+=1;
		batch.end();
		for(int i = 0; i<fyes.size();i++){
			fyes.get(i).update(Gdx.graphics.getDeltaTime());

		}
		for(int i = 0; i<coins.size();i++){
			coins.get(i).update(Gdx.graphics.getDeltaTime());
		}
		if (time > 2) {
			addNewObstacle();
			time = 0;
		}

		for (Movers s : obstacles) {
			s.update();
		}

		for (Movers f : fires) {
			f.update();
		}

		bg.update(Gdx.graphics.getDeltaTime());

		for (ParticleEffect f : fire) {
			f.update(Gdx.graphics.getDeltaTime());
			f.setPosition(dragon.hitbox.x + dragon.width - 15, dragon.hitbox.y + dragon.height / 2 + 15);
		}

		dragon.update(Gdx.graphics.getDeltaTime());



		int hitIndex = 0;
		//added all this
		for(int i = 0; i<coins.size();i++){
			if(coins.get(i).getHitBox().overlaps(dragon.hitbox)){
				coinTime+=Gdx.graphics.getDeltaTime();
				timerjustStarted = true;
				hitIndex = i;
				coins.remove(hitIndex);
				scoreS+=10;
			}
		}
		//and this
		if(coinTime<1&&timerjustStarted){
			batch.begin();
			coinsBitmap.setColor(1f, 1f, 1f, 1.0f);
			coinsBitmap.getData().setScale(2);
			coinsBitmap.draw(batch,hitCoin,dragon.x,dragon.y);
			batch.end();
			coinTime+=Gdx.graphics.getDeltaTime();
			if(coinTime>=1){
				timerjustStarted = false;
				coinTime-=1;
			}
		}
		displayCurrScore(scoreS);

		for(int i = 0; i<fyes.size();i++){
			if(fyes.get(i).getHitBox().overlaps(dragon.hitbox)){
				hitIndex = i;
				fyes.remove(hitIndex);
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			dragon.jump();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			ParticleEffect temp = pool.obtain();
			temp.setPosition(dragon.hitbox.x + dragon.width, dragon.hitbox.y + dragon.height / 2);
			temp.start();
			fire.add(temp);
			dragon.breathBox = new Rectangle(dragon.hitbox.x + dragon.width-50, dragon.hitbox.y, 250, dragon.height);
			for (int i = 0; i < obstacles.size(); i++) {
				if (dragon.breathBox.overlaps(obstacles.get(i).hitbox)) {
					obstacles.remove(i);
				}
			}
		}
	}
	//and all this
	public void displayCurrScore(int score){
		gameScore = "Score: " + score;
		gameCoins = "Coins Collected: " + coins;

		scoreBitmap = new BitmapFont();
		scoreBitmap.getData().setScale(2);


		//draw
		batch.begin();
		scoreBitmap.setColor(1f, 1f, 1f, 1.0f);
		scoreBitmap.draw(batch, gameScore, 100, 775);
		batch.end();
	}

	public void addNewObstacle() {
		int x = r.nextInt(3);

		if (x == 0) {
			makeOneWay();
		} else if (x == 1) {
			makeFalling();
		} else {
			makeRegular();
		}
	}

	public void makeOneWay() {
//		int startY = (int) (Math.random() * (height - 250) - 250) + 250;
		int range = (height - 250) - 250 + 1;
		int startY = r.nextInt(range) + 250;

		int changer;
		changer = changerArray[(int)Math.random() * 2];
		if (startY <= height - 250 && startY >= height - 350) {
			changer = -1;
		}
		if (startY >= 250 && startY <= 350) {
			changer = 1;
		}
		int endY = startY + (changer * 200);

		if (changer == -1) {    // go down from start
			Movers box1 = new Movers(width + 100, startY, 100, height - startY, "darkStoneStack.jpg", ObType.oneWay);
			Movers box2 = new Movers(width + 100, 0, 100, endY, "darkStoneStack.jpg", ObType.oneWay);
			obstacles.add(box1); obstacles.add(box2);

			if (r.nextBoolean()) {
				makeWall((int)box2.hitbox.x, (int)box2.hitbox.y + (int)box2.hitbox.height, (int)box1.hitbox.y, (int)box2.hitbox.width);
			}
		}
		if (changer == 1) {     // go up from start
			Movers box1 = new Movers(width + 100, endY, 100, height - startY, "darkStoneStack.jpg", ObType.oneWay);
			Movers box2 = new Movers(width + 100, 0, 100, startY, "darkStoneStack.jpg", ObType.oneWay);
			obstacles.add(box1); obstacles.add(box2);

			if (r.nextBoolean()) {
				makeWall((int)box1.hitbox.x, (int)box1.hitbox.y + (int)box1.hitbox.height, (int)box2.hitbox.y, (int)box1.hitbox.width);
			}
		}

		System.out.println(startY);
		System.out.println(endY);
	}

	public void makeFalling() {
		Movers falling = new Movers(width + 100, height + 100, 100, 100, "meteorite-clipart-31.gif", ObType.falling);
		obstacles.add(falling);
	}

	public void makeRegular() {
		int range = 4 - 2 + 1;
		int size = r.nextInt(range) + 2;

		ArrayList<Movers> platforms = new ArrayList<Movers>();

		while (platforms.size() < size) {
			int random = r.nextInt(height - 50);

			if (platforms.size() != 0) {
				for (int i=0; i<platforms.size(); i++) {
					if (Math.abs(random - platforms.get(i).hitbox.y) < 200) {
						continue;
					}
					Movers platform = new Movers(width + 100, random, 100, 50, "pillar.png", ObType.regular);
					obstacles.add(platform);
					platforms.add(platform);
				}
			} else {
				Movers platform = new Movers(width + 100, random, 100, 50, "pillar.png", ObType.regular);
				obstacles.add(platform);
				platforms.add(platform);

			}
		}

	}

	public void makeWall(int x, int y1, int y2, int w) {
		int height = Math.abs(y1 - y2);
		Movers wall = new Movers(x, y1, w, height, "wall.png", ObType.wall);
		obstacles.add(wall);
	}

	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
