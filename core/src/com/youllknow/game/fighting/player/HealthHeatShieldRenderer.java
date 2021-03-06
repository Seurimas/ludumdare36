package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.youllknow.game.IconManager;
import com.youllknow.game.fighting.HealthComponent;

public class HealthHeatShieldRenderer extends EntitySystem {
	private final Entity player;
	private final ShapeRenderer uiShapes;
	private final Batch uiBatch;
	private final Rectangle healthArea, heatArea;
	public HealthHeatShieldRenderer(Entity player, ShapeRenderer uiShapes, Batch uiBatch,
			Rectangle healthArea, Rectangle heatArea) {
		this.player = player;
		this.uiBatch = uiBatch;
		this.uiShapes = uiShapes;
		this.healthArea = new Rectangle(healthArea.x + 8, healthArea.y, healthArea.width - 8, healthArea.height);
		this.heatArea = new Rectangle(heatArea.x + 8, heatArea.y, heatArea.width - 8, heatArea.height);
	}
	@Override
	public void update(float deltaTime) {
		float healthPercent = player.getComponent(HealthComponent.class).getHealthPercent();
		float heatPercent = player.getComponent(PlayerComponent.class).getHeatLevel();
		float shieldPercent = player.getComponent(PlayerComponent.class).getShieldPercent();
		float chargePercent = player.getComponent(PlayerWeapon.class).getCharge();
		uiShapes.begin(ShapeType.Filled);
		uiShapes.setColor(Color.GREEN);
		uiShapes.rect(healthArea.x, healthArea.y, healthArea.width * healthPercent, healthArea.height - 5);
		if (heatPercent >= 0.95f)
			uiShapes.setColor(Color.RED);
		else
			uiShapes.setColor(Color.BLUE);
		uiShapes.rect(healthArea.x, healthArea.y + healthArea.height - 5, healthArea.width * shieldPercent, 5);
		uiShapes.setColor(Color.RED);
		uiShapes.rect(heatArea.x, heatArea.y, heatArea.width * heatPercent, heatArea.height - 5);
		uiShapes.setColor(Color.YELLOW);
		uiShapes.rect(heatArea.x, heatArea.y + heatArea.height - 5, heatArea.width * chargePercent, 5);
		uiShapes.end();
		uiShapes.begin(ShapeType.Line);
		uiShapes.setColor(Color.GREEN);
		uiShapes.rect(healthArea.x, healthArea.y, healthArea.width, healthArea.height);
		if (heatPercent >= 0.95f)
			uiShapes.setColor(Color.RED);
		else
			uiShapes.setColor(Color.BLUE);
		uiShapes.rect(healthArea.x, healthArea.y + healthArea.height - 5, healthArea.width, 5);
		uiShapes.setColor(Color.RED);
		uiShapes.rect(heatArea.x, heatArea.y, heatArea.width, heatArea.height);
		uiShapes.setColor(Color.YELLOW);
		uiShapes.rect(heatArea.x, heatArea.y + heatArea.height - 5, heatArea.width, 5);
		uiShapes.end();
		uiBatch.begin();
		uiBatch.setColor(Color.WHITE);
		uiBatch.draw(IconManager.getHealthIcon(), healthArea.x - 8, healthArea.y, 8, 8);
		uiBatch.setColor(Color.WHITE);
		uiBatch.draw(IconManager.getShieldIcon(), healthArea.x - 8, healthArea.y + 16, 8, 8);
		uiBatch.setColor(Color.WHITE);
		uiBatch.draw(IconManager.getHeatIcon(), heatArea.x - 8, heatArea.y, 8, 8);
		uiBatch.setColor(Color.WHITE);
		uiBatch.draw(IconManager.getChargeIcon(), heatArea.x - 8, heatArea.y + 16, 8, 8);
		uiBatch.end();
	}
}
