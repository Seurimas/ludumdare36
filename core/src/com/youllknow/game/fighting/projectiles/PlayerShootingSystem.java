package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.youllknow.game.fighting.player.PlayerWeapon;
import com.youllknow.game.input.InputMarshal;
import com.youllknow.game.utils.AshleyUtils;
import com.youllknow.game.utils.AshleyUtils.ComponentHandler;
import com.youllknow.game.utils.AshleyUtils.ComponentSubSystem;

public class PlayerShootingSystem extends EntitySystem implements ComponentHandler<ProjectileWeapon> {
	Family entityFamily = Family.all(ProjectileWeapon.class, PlayerWeapon.class).get();
	private final ComponentSubSystem<ProjectileWeapon> weapons = new ComponentSubSystem<ProjectileWeapon>(entityFamily, ProjectileWeapon.class, this);
	private final InputMarshal input;
	public PlayerShootingSystem(InputMarshal input) {
		this.input = input;
	}
	@Override
	public void update(Engine engine, Entity entity, ProjectileWeapon mainComponent, float delta) {
		if (input.isLeftJustClicked(false)) {
			mainComponent.fire(engine, entity, input.worldX, input.worldY);
		}
	}
	@Override
	public void update(float deltaTime) {
		if (input.mouseInUI())
			return;
		weapons.update(this, deltaTime);
	}
}
