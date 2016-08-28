package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;
import com.youllknow.game.wiring.PowerFlow;

public class ShieldSystem extends ComponentSystem<PlayerComponent> {

	public ShieldSystem() {
		super(PlayerComponent.class);
	}

	@Override
	public void update(Engine engine, Entity entity, PlayerComponent mainComponent, float delta) {
		mainComponent.updateShield(delta);
		mainComponent.drainHeat(delta);
		if (mainComponent.triggerShield()) {
			float amount = mainComponent.drainShieldByTrigger();
			HealthComponent health = entity.getComponent(HealthComponent.class);
			health.heal(30 * amount);
		}
	}

}
