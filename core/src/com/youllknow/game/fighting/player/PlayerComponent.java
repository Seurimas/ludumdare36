package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.youllknow.game.fighting.rendering.DenizenRenderer;
import com.youllknow.game.fighting.rendering.DenizenRendererComponent;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class PlayerComponent implements Component, DenizenRendererComponent {
	private static final float SHIELD_INTERVAL = 0.6f;
	private static final float HEAT_SINK = 0.025f;
	private static final float VERTICAL_FRAMES_IN_SHEET = 4f;
	private static final float HORIZONTAL_FRAMES = 4f;
	static {
		DenizenRenderer.addRenderer(PlayerComponent.class);
	}
	public boolean walking = false;
	private float shieldEnergy = 1;
	private float shieldProgress = 0;
	private float heatLevel = 0;
	private float duration = 0;
	public float screenLeft = 0;
	private final Texture sprite;
	public Energy weaponSettings = Energy.RED;
	public PlayerComponent(Texture texture4Frame) {
		sprite = texture4Frame;
	}
	public void heatUp(float amount) {
		heatLevel += amount;
	}
	public void coolDown(float amount) {
		heatLevel -= amount;
	}
	public float getHeatLevel() {
		return heatLevel;
	}
	public void toggleWeaponSettings() {
		switch (weaponSettings) {
		case BLUE:
			weaponSettings = Energy.BLUE;
			break;
		case GREEN:
			weaponSettings = Energy.RED;
			break;
		case RED:
			weaponSettings = Energy.GREEN;
			break;
		default:
			weaponSettings = Energy.RED;
			break;
		
		}
	}
	public void draw(Batch batch, Entity player, Rectangle area, float deltaTime) {
		int frame = 0;
		if (walking) {
			duration += deltaTime;
			frame = (int)(duration * 8) % 4;
		} else {
			duration = 0;
		}
		batch.draw(sprite, area.x, area.y, area.width, area.height, 
				 frame / HORIZONTAL_FRAMES, 1 / VERTICAL_FRAMES_IN_SHEET, 
				 (frame + 1) / HORIZONTAL_FRAMES, 0);
		PlayerWeapon weapon = player.getComponent(PlayerWeapon.class);
		weapon.draw(batch, area);
	}
	public float drainShield() {
		float temp = shieldEnergy;
		shieldEnergy = 0;
		return MathUtils.clamp(temp, 0, 1);
	}
	public void updateShield(float deltaTime) {
		shieldProgress += deltaTime;
	}
	private final float SHIELD_TRIGGER = 0.125f;
	public boolean triggerShield() {
		if (heatLevel >= 0.95f)
			return false;
		if (shieldProgress > SHIELD_INTERVAL) {
			shieldProgress = 0;
			return true;
		}
		return false;
	}
	public float drainShieldByTrigger() {
		if (shieldEnergy >= SHIELD_TRIGGER) {
			shieldEnergy -= SHIELD_TRIGGER;
			return SHIELD_TRIGGER;
		} else {
			return drainShield();
		}
	}
	public void shieldUp(float gainedShield) {
		shieldEnergy += gainedShield;
		if (shieldEnergy > 1)
			shieldEnergy = 1;
	}
	public float getShieldPercent() {
		return shieldEnergy;
	}
	public void drainHeat(float delta) {
		heatLevel -= delta * HEAT_SINK;
		if (heatLevel < 0)
			heatLevel = 0;
	}
}
