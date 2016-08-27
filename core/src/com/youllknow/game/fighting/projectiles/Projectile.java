package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class Projectile implements Component {
	public static interface Behavior {
		public boolean onHit(Engine engine, Entity self, Entity target);
	}
	private final Entity source;
	private final Behavior behavior;
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Projectile(Entity source, Vector2 position, Vector2 velocity,
			Behavior behavior) {
		this.source = source;
		this.behavior = behavior;
		this.position.set(position);
		this.velocity.set(velocity);
	}
	public void update(float delta) {
		position.add(velocity.x * delta, velocity.y * delta);
	}
	public void hit(Engine engine, Entity projEntity, Entity entity) {
		if (entity != source) {
			if (behavior.onHit(engine, projEntity, entity))
				engine.removeEntity(projEntity);
		}
	}
	
}
