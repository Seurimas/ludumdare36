package com.youllknow.game.fighting.projectiles.behaviors;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.fighting.projectiles.Projectile.HitBehavior;

public class SingleShotBehavior implements HitBehavior {
	private final float damage;
	public SingleShotBehavior(float damage) {
		this.damage = damage;
	}
	@Override
	public boolean onHit(Engine engine, Entity self, Entity target) {
		HealthComponent targetHealth = target.getComponent(HealthComponent.class);
		if (targetHealth != null) {
			targetHealth.damage(damage);
			return true;
		}
		return false;
	}

}
