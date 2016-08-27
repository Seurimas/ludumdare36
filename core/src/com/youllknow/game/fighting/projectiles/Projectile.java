package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class Projectile implements Component {
	public static interface HitBehavior {
		public boolean onHit(Engine engine, Entity self, Entity target);
	}
	public static interface TargetBehavior {
		public boolean doesHit(Entity target);
	}
	private final Entity source;
	private final HitBehavior hitBehavior;
	private final TargetBehavior targetBehavior;
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Projectile(Entity source, Vector2 position, Vector2 velocity,
			HitBehavior hit, TargetBehavior target) {
		this.source = source;
		hitBehavior = hit;
		targetBehavior = target;
		this.position.set(position);
		this.velocity.set(velocity);
	}
	public void update(float delta) {
		position.add(velocity.x * delta, velocity.y * delta);
	}
	public void hit(Engine engine, Entity projEntity, Entity entity) {
		if (targetBehavior.doesHit(entity)) {
			if (hitBehavior.onHit(engine, projEntity, entity))
				engine.removeEntity(projEntity);
		}
	}
	
}
