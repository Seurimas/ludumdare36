package com.youllknow.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.fighting.player.PlayerComponent;

public class HealthHeatRenderer extends EntitySystem {
	private final Entity player;
	private final ShapeRenderer uiShapes;
	private final Batch uiBatch;
	private final Rectangle healthArea, heatArea;
	public HealthHeatRenderer(Entity player, ShapeRenderer uiShapes, Batch uiBatch,
			Rectangle healthArea, Rectangle heatArea) {
		this.player = player;
		this.uiBatch = uiBatch;
		this.uiShapes = uiShapes;
		this.healthArea = healthArea;
		this.heatArea = heatArea;
	}
	@Override
	public void update(float deltaTime) {
		float healthPercent = player.getComponent(HealthComponent.class).getHealthPercent();
		float heatPercent = player.getComponent(PlayerComponent.class).getHeatLevel();
		uiShapes.begin(ShapeType.Filled);
		uiShapes.setColor(Color.GREEN);
		uiShapes.rect(healthArea.x, healthArea.y, healthArea.width * healthPercent, healthArea.height);
		uiShapes.setColor(Color.RED);
		uiShapes.rect(heatArea.x, heatArea.y, heatArea.width * heatPercent, heatArea.height);
		uiShapes.end();
		uiShapes.begin(ShapeType.Line);
		uiShapes.setColor(Color.GREEN);
		uiShapes.rect(healthArea.x, healthArea.y, healthArea.width, healthArea.height);
		uiShapes.setColor(Color.RED);
		uiShapes.rect(heatArea.x, heatArea.y, heatArea.width, heatArea.height);
		uiShapes.end();
	}
}
