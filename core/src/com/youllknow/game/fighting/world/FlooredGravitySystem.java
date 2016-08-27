package com.youllknow.game.fighting.world;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.utils.AshleyUtils;

public class FlooredGravitySystem extends EntitySystem {
	private static final Family entityFamily = Family.all(WorldDenizen.class).get();
	private final Iterable<WorldDenizen> denizens = AshleyUtils.getComponentIterable(this, entityFamily, WorldDenizen.class);
	private static final Vector2 gravity = new Vector2(0, -3000f);
	@Override
	public void update(float deltaTime) {
		for (WorldDenizen denizen : denizens) {
			if (denizen.getY() <= 0 && denizen.getVelocityY() <= 0)
				denizen.hitFloor(0);
			else
				denizen.applyGravity(gravity, deltaTime);
		}
	}
}
