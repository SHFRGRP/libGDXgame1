package com.shfrgrp.games.game1;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.math.*;
import java.util.*;
import com.shfrgrp.games.game1.objects.*;
import com.shfrgrp.games.game1.utils.*;

public class MyGdxGame implements ApplicationListener
{
	OrthographicCamera camera;
    Animation walkAnimation;
	TextureRegion backgroundTexture;
	TextureRegion rockTexture;
	Sound collisionSound;
	BitmapFont font;
    SpriteBatch batch;

    float time;
	Player player = new Player();
	//Rectangle player.position;
	//Vector2 player.velocity;
	
	List<Rectangle> rockPositions;

    @Override
    public void create()
	{
		// Load background 
		Texture texture = new Texture(Gdx.files.internal("skyBackground.jpg"));
		backgroundTexture = new TextureRegion(texture, 0, 0, 2048, 563);
		
		// Load and position rocks
		Texture texture2 = new Texture(Gdx.files.internal("rock.png"));
		rockTexture = new TextureRegion(texture2, 25, 0, 250, 250);
		rockPositions = new ArrayList<Rectangle>();
		int x = 1800;
		for (int i = 0; i < 60; i++)
		{
			rockPositions.add(new Rectangle(x, 0, 100, 100));
			x += 600 + new Random().nextInt(600);
		}
		
		// Create run animation
        Texture walkSheet = new Texture(Gdx.files.internal("runAnimation.png"));
		int FRAME_COLS = 6;
		int FRAME_ROWS = 5;
        
        walkAnimation = Utils.getAnimation(walkSheet, FRAME_COLS, FRAME_ROWS);

		font = new BitmapFont();
		
		collisionSound = Gdx.audio.newSound(Gdx.files.internal("collision.wav"));
		
        batch = new SpriteBatch();
		camera = new OrthographicCamera();
		resetGame();
    }

    @Override
    public void render()
	{
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
        batch.begin();
		
		// Draw background
		for (int i = 0; i < 30; i++)
			batch.draw(backgroundTexture, i * 2900, 0, 2900, 800);
		
		// Draw rocks
		for (Rectangle r : rockPositions)
			batch.draw(rockTexture, r.x, r.y, r.width, r.height);
			
		// Draw man
        batch.draw(walkAnimation.getKeyFrame(time, true), player.position.x, player.position.y, player.position.width, player.position.height);
		
		font.draw(batch, (int) (player.position.x / 70) + "m", camera.position.x - 10, 30);
        batch.end();

		// Move man
		time += Gdx.graphics.getDeltaTime();
		player.position.x += player.velocity.x * Gdx.graphics.getDeltaTime();
		player.position.y += player.velocity.y * Gdx.graphics.getDeltaTime();
		player.velocity.y -= 1000 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isTouched() && player.position.y == 0)
		{
			player.velocity.y = 500;
		}
		if (player.position.y < 0) 
		{
			player.position.y = 0;
			player.velocity.y = 0;
		}

		// Move camera
		camera.translate((player.velocity.x - camera.viewportWidth / 80) * Gdx.graphics.getDeltaTime(), 0);
		
		// Detect collision
		for (Rectangle r : rockPositions)
		{
			if (r.overlaps(player.position) && r.getCenter(new Vector2()).dst(player.position.getCenter(new Vector2())) < 120)
			{
				collisionSound.play();
				resetGame();
				break;
			}
		}
    }
	
	private void resetGame()
	{
		configureCamera();
		player.position = new Rectangle(0, 0, 200, 200);
		player.velocity = new Vector2(500, 0);
	}

	private void configureCamera()
	{
		if (Gdx.graphics.getHeight() < Gdx.graphics.getWidth())
			camera.setToOrtho(false, 800, 800 * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		else
			camera.setToOrtho(false, 800 * Gdx.graphics.getWidth() / Gdx.graphics.getHeight(), 800);
	}

	@Override
    public void dispose()
	{
        batch.dispose();
    }

    @Override
    public void resize(int width, int height)
	{
		resetGame();
    }

    @Override
    public void pause()
	{
    }

    @Override
    public void resume()
	{
    }
}
