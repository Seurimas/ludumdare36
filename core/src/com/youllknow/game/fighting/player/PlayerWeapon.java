package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.HealthComponent.DamageType;
import com.youllknow.game.fighting.projectiles.NonOwnerTargetBehavior;
import com.youllknow.game.fighting.projectiles.Projectile;
import com.youllknow.game.fighting.projectiles.behaviors.MultiShotBehavior;
import com.youllknow.game.wiring.PowerFlow;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class PlayerWeapon implements Component {
	private final TextureRegion sprite;
	private float rotation = 0;
	private float chargeStrength = 0;
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
			playerComponent.heatUp(0.05f);
		} else if (shutOffEnergy.equals(Energy.GREEN)) {
			playerComponent.heatUp(0.1f);
		}
		float extraStrength = 0;
		if (drainEnergy.equals(Energy.BLUE)) {
			extraStrength = playerComponent.drainShield();
		}
		if (fireChargeEnergy.equals(Energy.GREEN)) {
			chargeStrength += 0.1f;
//			return;
		} else if (fireChargeEnergy.equals(Energy.RED)) {
			extraStrength += chargeStrength;
			chargeStrength = 0;
		}
		fireShot(engine, entity, worldX, worldY, extraStrength);
	}
	private static final Vector2 temp = new Vector2();
	private void fireShot(Engine engine, Entity entity, float worldX, float worldY, float extraStrength) {
		int shotCount = (int)(extraStrength / 0.5f) + 1;
		float damage = (1 + extraStrength) * 5;
		Entity dummy = new Entity();
		WorldDenizen denizen = entity.getComponent(WorldDenizen.class);
		temp.set(denizen.getCenter());
		temp.scl(-1).add(worldX, worldY);
		temp.nor().scl(600f);
//		temp.add(denizen.getVelocity());
		dummy.add(new Projectile(entity, denizen.getCenter(), temp, new MultiShotBehavior(shotCount, damage > 15 ? DamageType.EXPLOSIVE : DamageType.ENERGY, damage), 
				new NonOwnerTargetBehavior().getBehavior(entity)));
		engine.addEntity(dummy);
	}
	private void rotateTowards(Entity entity, float worldX, float worldY) {
		Vector2 direction = entity.getComponent(WorldDenizen.class).getCenter();
		direction.scl(-1).add(worldX, worldY);
		rotate(direction);
	}
	public float getCharge() {
		return chargeStrength;
	}
}
