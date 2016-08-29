package com.youllknow.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

public class GameOverScreen implements Screen {
	private final BitmapFont font = new BitmapFont();
	private float progress = 0;
	private float duration = 3f;
	private final float FADE_IN = 1f;
	private final Batch uiBatch;
	private final LudumDare36Game game;
	public GameOverScreen(Batch uiBatch, LudumDare36Game game) {
		this.uiBatch = uiBatch;
		this.game = game;
	}
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Color black = new Color(0, 0, 0, 1);
		Color white = new Color(1, 1, 1, 1);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		uiBatch.begin();
		font.setColor(black.lerp(white, Math.min(progress / FADE_IN, 1)));
		font.draw(uiBatch, "And thus, the weapon sank again...", 0, 300, 800, Align.center, true);
		uiBatch.end();
		progress += delta;
		if (progress > duration)
			game.restart();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
