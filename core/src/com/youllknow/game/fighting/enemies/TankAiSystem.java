package com.youllknow.game.fighting.enemies;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.MainGameScreen;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class TankAiSystem extends ComponentSystem<TankEnemy> {
	private final Vector2 tankMovementLeft = new Vector2(-3000, 0);
	private final Vector2 tankMovementRight = new Vector2(3000, 0);
	public TankAiSystem() {
		super(TankEnemy.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Engine engine, Entity entity, TankEnemy mainComponent, float delta) {
		mainComponent.update(delta);
		Entity player = mainComponent.player;
		WorldDenizen playerDenizen = player.getComponent(WorldDenizen.class);
		WorldDenizen tankDenizen = entity.getComponent(WorldDenizen.class);
		float distanceToPlayer = tankDenizen.getX() - playerDenizen.getX() - playerDenizen.getWidth();
		if (Math.abs(distanceToPlayer) < MainGameScreen.SCREEN_WIDTH - 300) {
			if (mainComponent.canFire())
				mainComponent.fire(engine, entity, playerDenizen.getCenter().add(playerDenizen.getVelocityX() + 25, playerDenizen.getVelocityY()));
			tankDenizen.applyFriction(1000f * delta);
		} else if (distanceToPlayer > 0) {
			tankDenizen.applyImpulse(tankMovementLeft, delta);
		} else if (distanceToPlayer < 0) {
			tankDenizen.applyImpulse(tankMovementRight, delta);
		}
	}

}
