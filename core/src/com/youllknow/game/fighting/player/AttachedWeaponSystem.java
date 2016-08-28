package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.projectiles.ProjectileWeapon;
import com.youllknow.game.utils.AshleyUtils.ComponentHandler;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class AttachedWeaponSystem extends ComponentSystem<AttachedWeapon> {

	public AttachedWeaponSystem() {
		super(AttachedWeapon.class);
	}

	@Override
	public void update(Engine engine, Entity entity, AttachedWeapon mainComponent, float delta) {
		ProjectileWeapon weapon = entity.getComponent(ProjectileWeapon.class);
		mainComponent.moveSource(weapon);
	}
}
