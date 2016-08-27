package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class ProjectileMovementSystem extends ComponentSystem<Projectile> {

	public ProjectileMovementSystem() {
		super(Projectile.class);
	}

	@Override
	public void update(Engine engine, Entity entity, Projectile mainComponent, float delta) {
		mainComponent.update(delta);
	}

}
