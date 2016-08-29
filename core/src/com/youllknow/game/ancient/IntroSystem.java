package com.youllknow.game.ancient;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.MainGameScreen;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.player.PlayerComponent;
import com.youllknow.game.utils.TooltipManager;

public class IntroSystem extends EntitySystem {
	private final Entity player;
	private final Music music;
	private final Music continueMusic;
	private final TooltipManager title;
	private final ShapeRenderer uiShapes;
	public IntroSystem(Entity player, Music music, Music continueMusic, ShapeRenderer uiShapes, TooltipManager tooltip) {
		this.player = player;
		this.music = music;
		this.continueMusic = continueMusic;
		this.title = tooltip;
		this.uiShapes = uiShapes;
		if (progress > FIN)
			player.getComponent(PlayerComponent.class).screenLeft += 50 * 60;
	}
	private static float progress = 0;
	private final float FADE_IN = 2f;
	private final float EXPLAIN_MOVEMENT = 10f;
	private final float EXPLAIN_WEAPONS = 20f;
	private final float EXPLAIN_SCHEMATICS = 30f; 
	private final float EXPLAIN_SCHEMATICS_EXTENDED = 40f; 
	private final float FIN = 60f; 
	@Override
	public void update(float deltaTime) {
		if (progress < 20 && !music.isPlaying()) {
			music.stop();
			music.play();
			music.setPosition(0);
			music.setLooping(false);
		} else if (!music.isPlaying() && !continueMusic.isPlaying()) {
			continueMusic.play();
			continueMusic.setLooping(true);
		}
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		progress += deltaTime;
		if (progress < FADE_IN) {
			title.tooltip = "We discovered it under the sea...";
		} else if (progress < EXPLAIN_MOVEMENT) {
			title.tooltip = "An ancient weapon of untold power...";
		} else if (progress < EXPLAIN_WEAPONS) {
			title.tooltip = "The navigation systems were still intact (WASD to move)";
		} else if (progress < EXPLAIN_SCHEMATICS) {
			title.tooltip = "The weapons still devestating (left-click to fire phasers; Q to change weapon settings)";
		} else {
			String tooltipBase = "Its workings, mysterious (click on schematics to alter logic)";
			if (title.tooltip != null && (!title.tooltip.startsWith(tooltipBase)))
				title.tooltip += "\n" + tooltipBase;
			else
				title.tooltip = tooltipBase;
			if (progress >= EXPLAIN_SCHEMATICS_EXTENDED)
				title.tooltip += "\nMastering this ancient technology will be difficult.";
		}
		if (progress < EXPLAIN_WEAPONS) {
			drawFade();
		}
		if (progress < EXPLAIN_SCHEMATICS + 5) {
			hideUi();
		} else if (progress >= FIN) {
			getEngine().removeSystem(this);
		}
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	private void drawFade() {
		uiShapes.begin(ShapeType.Filled);
		float gradientLeft = Math.min(1, progress / FADE_IN) * 150;
		if (progress >= EXPLAIN_MOVEMENT) {
			float movementPhase = (progress - EXPLAIN_MOVEMENT) / (EXPLAIN_WEAPONS - EXPLAIN_MOVEMENT);
			gradientLeft += Math.min(4, movementPhase * 4) * (MainGameScreen.SCREEN_WIDTH - 150);
		}
		Color centerColor = new Color(0, 0, 0, 0);
		Color outerColor = new Color(0, 0, 0, 1);
		uiShapes.rect(0, MainGameScreen.LOWER_UI_HEIGHT, gradientLeft, MainGameScreen.WORLD_HEIGHT + 64, 
				centerColor, outerColor, outerColor, centerColor);
		uiShapes.rect(gradientLeft, MainGameScreen.LOWER_UI_HEIGHT, MainGameScreen.SCREEN_WIDTH - gradientLeft, MainGameScreen.WORLD_HEIGHT + 64, 
				outerColor, outerColor, outerColor, outerColor);
		uiShapes.end();
	}
	private void hideUi() {
		Color centerColor = new Color(0, 0, 0, 0);
		Color outerColor = new Color(0, 0, 0, 1);
		float weaponPhase = (progress - EXPLAIN_WEAPONS) / 3f;
		float bottom = MainGameScreen.LOWER_UI_HEIGHT - (25 * weaponPhase);
		if (weaponPhase < 0) {
			bottom = MainGameScreen.LOWER_UI_HEIGHT;
		} else if (progress > EXPLAIN_SCHEMATICS) {
			float schematicsPhase = Math.max(0, 1 - (progress - EXPLAIN_SCHEMATICS) / 5f);
			bottom *= schematicsPhase;
		}
		float remainderHeight = MainGameScreen.LOWER_UI_HEIGHT - bottom;
		uiShapes.begin(ShapeType.Filled);
		uiShapes.rect(0, bottom, MainGameScreen.SCREEN_WIDTH, remainderHeight, 
				outerColor, outerColor, centerColor, centerColor);
		uiShapes.rect(0, 0, MainGameScreen.SCREEN_WIDTH, bottom, 
				outerColor, outerColor, outerColor, outerColor);
		uiShapes.end();
	}
}
