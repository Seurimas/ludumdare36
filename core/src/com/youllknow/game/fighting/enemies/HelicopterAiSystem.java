package com.youllknow.game.fighting.enemies;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.MainGameScreen;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class HelicopterAiSystem extends ComponentSystem<HelicopterEnemy> {
	public HelicopterAiSystem() {
		super(HelicopterEnemy.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Engine engine, Entity entity, HelicopterEnemy mainComponent, float delta) {
		mainComponent.update(delta);
		Entity player = mainComponent.player;
		WorldDenizen playerDenizen = player.getComponent(WorldDenizen.class);
		WorldDenizen heliDenizen = entity.getComponent(WorldDenizen.class);
		float distanceToPlayer = heliDenizen.getX() - playerDenizen.getX() - playerDenizen.getWidth();
		Vector2 helicopterMovement = new Vector2();
		if (distanceToPlayer < 200) {
			helicopterMovement.x = 300f;
		} else if (distanceToPlayer > 600) {
			helicopterMovement.x = -300f;
		}
		if (distanceToPlayer < MainGameScreen.SCREEN_WIDTH - 400) {
			if (mainComponent.canFire()) {
				mainComponent.fire(engine, entity, playerDenizen.getCenter());
				heliDenizen.applyImpulse(new Vector2(0, MathUtils.random(-1, 1) * 1000));
			}
		}
		if (helicopterMovement.isZero())
			heliDenizen.applyFriction(1000f * delta);
		else
			heliDenizen.applyImpulse(helicopterMovement, delta);
		if ((heliDenizen.getY() > MainGameScreen.WORLD_HEIGHT - 32 && heliDenizen.getVelocityY() > 0) ||
				(heliDenizen.getY() < 0 && heliDenizen.getVelocityY() < 0))
			heliDenizen.setVelocity(heliDenizen.getVelocity().scl(0, -1));
	}

}
