package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class Projectile implements Component {
	private final Entity source;
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Projectile(Entity source, Vector2 position, Vector2 velocity) {
		this.source = source;
		this.position.set(position);
		this.velocity.set(velocity);
	}
	public void update(float delta) {
		position.add(velocity.x * delta, velocity.y * delta);
	}
	public void hit(Engine engine, Entity entity) {
		if (entity != source) {
			System.out.println("OW!");
		}
	}
	
}
