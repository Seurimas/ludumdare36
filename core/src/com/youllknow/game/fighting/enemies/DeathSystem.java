package com.youllknow.game.fighting.enemies;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class DeathSystem extends ComponentSystem<HealthComponent> {

	public DeathSystem() {
		super(HealthComponent.class);
	}

	@Override
	public void update(Engine engine, Entity entity, HealthComponent mainComponent, float delta) {
		if (mainComponent.getHealth() <= 0) {
			mainComponent.handleDeath(engine, entity);
		}
	}
}
