package com.youllknow.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.youllknow.game.input.InputMarshal;

public class LudumDare36Game extends Game {
	public SpriteBatch batch, uiBatch;
	public ShapeRenderer shapes, uiShapes;
	Texture img;
	public InputMarshal input;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		shapes = new ShapeRenderer();
		uiShapes = new ShapeRenderer();
		input = new InputMarshal();
		img = new Texture("badlogic.jpg");
		setScreen(new MainGameScreen(this));
	}
}
