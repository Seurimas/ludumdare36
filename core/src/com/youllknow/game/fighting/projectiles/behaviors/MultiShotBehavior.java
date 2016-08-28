package com.youllknow.game.fighting.projectiles.behaviors;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.fighting.HealthComponent.DamageType;
import com.youllknow.game.fighting.projectiles.Projectile.HitBehavior;

public class MultiShotBehavior implements HitBehavior {
	private int hitsLeft;
	private final DamageType damageType;
	private final float damage;
	public MultiShotBehavior(int shotCount, DamageType damageType, float damage) {
		hitsLeft = shotCount;
		this.damageType = damageType;
		this.damage = damage;
	}
	@Override
	public boolean onHit(Engine engine, Entity self, Entity target) {
		HealthComponent targetHealth = target.getComponent(HealthComponent.class);
		if (targetHealth != null) {
			targetHealth.damage(damageType, damage);
			hitsLeft--;
			return hitsLeft <= 0;
		}
		return false;
	}

}
