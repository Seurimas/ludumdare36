package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.fighting.HealthComponent.DamageType;
import com.youllknow.game.fighting.projectiles.ProjectileWeapon;
import com.youllknow.game.input.InputMarshal;
import com.youllknow.game.utils.AshleyUtils;
import com.youllknow.game.utils.AshleyUtils.ComponentHandler;
import com.youllknow.game.utils.AshleyUtils.ComponentSubSystem;

public class PlayerShootingSystem extends EntitySystem implements ComponentHandler<PlayerWeapon> {
	Family entityFamily = Family.all(PlayerWeapon.class).get();
	private final ComponentSubSystem<PlayerWeapon> weapons = new ComponentSubSystem<PlayerWeapon>(entityFamily, PlayerWeapon.class, this);
	private final InputMarshal input;
	public PlayerShootingSystem(InputMarshal input) {
		this.input = input;
	}
	@Override
	public void update(Engine engine, Entity entity, PlayerWeapon mainComponent, float delta) {
		if (input.isLeftJustClicked(false)) {
			mainComponent.fire(engine, entity, input.worldX, input.worldY);
		}
		if (Gdx.input.isKeyJustPressed(Keys.Q)) {
			entity.getComponent(PlayerComponent.class).toggleWeaponSettings();
		}
		if (Gdx.input.isKeyJustPressed(Keys.Y))
			entity.getComponent(HealthComponent.class).damage(DamageType.ENERGY, 1000);
	}
	@Override
	public void update(float deltaTime) {
		if (input.mouseInUI())
			return;
		weapons.update(this, deltaTime);
	}
}
