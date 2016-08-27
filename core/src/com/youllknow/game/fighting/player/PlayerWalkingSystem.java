package com.youllknow.game.fighting.player;

import java.util.Iterator;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.MainGameScreen;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.utils.AshleyUtils;

public class PlayerWalkingSystem extends EntitySystem {
	private static final Family entityFamily = Family.all(PlayerComponent.class, WorldDenizen.class).get();
	private Iterable<WorldDenizen> walkers = AshleyUtils.getComponentIterable(this, entityFamily, WorldDenizen.class);
	
	@Override
	public void update(float deltaTime) {
		Vector2 walkImpulse = new Vector2();
		Vector2 jumpImpulse = new Vector2();
		if (Gdx.input.isKeyPressed(Keys.A)) {
			walkImpulse.x = -10000f;
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			walkImpulse.x = 10000f;
		}
		jumpImpulse.y = 10000f;
		for (Entity player : getEngine().getEntitiesFor(entityFamily)) {
			WorldDenizen walker = player.getComponent(WorldDenizen.class);
			if (walkImpulse.isZero()) {
				walker.applyFriction(10000 * deltaTime);
				player.getComponent(PlayerComponent.class).walking = false;
			} else {
				walker.applyImpulse(walkImpulse, deltaTime);
				player.getComponent(PlayerComponent.class).walking = true;
			}
			if (walker.getVelocityY() == 0 && Gdx.input.isKeyJustPressed(Keys.SPACE))
				walker.applyImpulse(jumpImpulse);
		}
	}
}
