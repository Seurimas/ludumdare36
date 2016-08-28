package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.HealthComponent.DamageStrength;
import com.youllknow.game.fighting.HealthComponent.DamageType;
import com.youllknow.game.fighting.projectiles.NonOwnerTargetBehavior;
import com.youllknow.game.fighting.projectiles.Projectile;
import com.youllknow.game.fighting.projectiles.behaviors.MultiShotBehavior;
import com.youllknow.game.fighting.projectiles.behaviors.SingleShotBehavior;
import com.youllknow.game.fighting.projectiles.rendering.ColorCodedProjectileRenderer;
import com.youllknow.game.wiring.PowerFlow;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class PlayerWeapon implements Component {
	private final TextureRegion sprite;
	private float rotation = 0;
	private float chargeStrength = 0;
	private final float MAX_CHARGE_STRENGTH = 10;
	private PowerFlow powerFlow;
	public Energy drainEnergy, fireChargeEnergy, shutOffEnergy;
	public PlayerWeapon(TextureRegion sprite) {
		this.sprite = sprite;
	}
	public void setPowerFlow(PowerFlow powerFlow) {
		this.powerFlow = powerFlow;
	}
	public void rotate(Vector2 targetDir) {
		rotation = targetDir.angle();
	}
	public void draw(Batch batch, Rectangle playerBox) {
		float x = playerBox.x + playerBox.width / 2 - sprite.getRegionWidth() / 2;
		float y = playerBox.y + playerBox.height / 2 - sprite.getRegionHeight() / 2;
		float originX = sprite.getRegionWidth() / 2;
		float originY = sprite.getRegionHeight() / 2;
		batch.draw(sprite, x, y, originX, originY, 
				sprite.getRegionWidth(), sprite.getRegionHeight(), 1, 1, rotation);
	}
	public void fire(Engine engine, Entity entity, float worldX, float worldY) {
		powerFlow.calculate();
		rotateTowards(entity, worldX, worldY);
		PlayerComponent playerComponent = entity.getComponent(PlayerComponent.class);
		if (shutOffEnergy.equals(Energy.RED)) {
			return;
		} else if (shutOffEnergy.equals(Energy.BLUE)) {
			playerComponent.heatUp(0.025f);
		} else if (shutOffEnergy.equals(Energy.GREEN)) {
			playerComponent.heatUp(0.05f);
		}
		float extraStrength = 0;
		if (drainEnergy.equals(Energy.BLUE)) {
			extraStrength = playerComponent.drainShield();
		}
		if (fireChargeEnergy.equals(Energy.GREEN)) {
			chargeStrength += 0.25f;
			if (chargeStrength > MAX_CHARGE_STRENGTH)
				chargeStrength = MAX_CHARGE_STRENGTH;
			return;
		} else if (fireChargeEnergy.equals(Energy.RED)) {
			extraStrength += chargeStrength;
			chargeStrength = Math.max(0, chargeStrength - 0.1f);
		}
		fireShot(engine, entity, worldX, worldY, extraStrength);
	}
	private static final Vector2 temp = new Vector2();
	private void fireShot(Engine engine, Entity entity, float worldX, float worldY, float extraStrength) {
		int shotCount = (int)(extraStrength / 0.5f) + 1;
		float damage = (1 + extraStrength) * 5;
		DamageType damageType = damage > 15 ? DamageType.EXPLOSIVE : DamageType.ENERGY;
		sprayAt(engine, entity, worldX, worldY, damage, damageType, shotCount);
	}
	private void sprayAt(Engine engine, Entity entity, float worldX, float worldY, float damage,
			DamageType damageType, int shotCount) {
		WorldDenizen denizen = entity.getComponent(WorldDenizen.class);
		temp.set(denizen.getCenter());
		temp.scl(-1).add(worldX, worldY);
		temp.nor().scl(600f);
		for (int i = 0;i < shotCount;i++) {
			Entity dummy = new Entity();
			dummy.add(new Projectile(entity, denizen.getCenter(), temp, new SingleShotBehavior(damageType, damage), 
					new NonOwnerTargetBehavior().getBehavior(entity)));
			dummy.add(new ColorCodedProjectileRenderer(damageType.color, DamageStrength.color(damage)));
			engine.addEntity(dummy);
			temp.rotate(30);
		}
	}
	private void rotateTowards(Entity entity, float worldX, float worldY) {
		Vector2 direction = entity.getComponent(WorldDenizen.class).getCenter();
		direction.scl(-1).add(worldX, worldY);
		rotate(direction);
	}
	public float getCharge() {
		return chargeStrength / MAX_CHARGE_STRENGTH;
	}
}
