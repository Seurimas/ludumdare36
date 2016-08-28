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

public class PlayerFlyingSystem extends EntitySystem {
	private static final Family entityFamily = Family.all(PlayerComponent.class, WorldDenizen.class).get();
	private Iterable<WorldDenizen> walkers = AshleyUtils.getComponentIterable(this, entityFamily, WorldDenizen.class);
	
	@Override
	public void update(float deltaTime) {
		Vector2 targetMovement = new Vector2();
		if (Gdx.input.isKeyPressed(Keys.A)) {
			targetMovement.x = -200;
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			targetMovement.x = 200;
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			targetMovement.y = 200;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			targetMovement.y = -200;
		}
		for (Entity player : getEngine().getEntitiesFor(entityFamily)) {
			WorldDenizen walker = player.getComponent(WorldDenizen.class);
			if (targetMovement.isZero()) {
				player.getComponent(PlayerComponent.class).walking = false;
			} else {
				player.getComponent(PlayerComponent.class).walking = true;
			}
			walker.setVelocity(targetMovement);
		}
	}
}
