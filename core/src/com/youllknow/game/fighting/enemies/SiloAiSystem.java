package com.youllknow.game.fighting.enemies;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.MainGameScreen;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class SiloAiSystem extends ComponentSystem<SiloEnemy> {
	private final Vector2 tankMovementLeft = new Vector2(-3000, 0);
	private final Vector2 tankMovementRight = new Vector2(3000, 0);
	public SiloAiSystem() {
		super(SiloEnemy.class);
	}

	@Override
	public void update(Engine engine, Entity entity, SiloEnemy mainComponent, float delta) {
		mainComponent.update(delta);
		mainComponent.moveSource(null);
		Entity player = mainComponent.player;
		WorldDenizen playerDenizen = player.getComponent(WorldDenizen.class);
		WorldDenizen siloDenizen = entity.getComponent(WorldDenizen.class);
		float distanceToPlayer = siloDenizen.getX() - playerDenizen.getX() - playerDenizen.getWidth();
		if (Math.abs(distanceToPlayer) < MainGameScreen.SCREEN_WIDTH - 300) {
			if (mainComponent.canFire())
				mainComponent.fire(engine, entity, playerDenizen.getCenter());
		}
	}

}
