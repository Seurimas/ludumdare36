package com.youllknow.game.fighting.enemies;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.youllknow.game.fighting.HealthComponent.DeathBehavior;

public class ExplosionDeathBehavior implements DeathBehavior {

	@Override
	public void onDeath(Engine engine, Entity entity) {
		engine.removeEntity(entity);
	}

}
