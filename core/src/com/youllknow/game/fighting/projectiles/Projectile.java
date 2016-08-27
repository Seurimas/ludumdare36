package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Projectile implements Component {
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	public Projectile(Vector2 position, Vector2 velocity) {
		this.position.set(position);
		this.velocity.set(velocity);
	}
	public Projectile(Vector2 source, float worldX, float worldY) {
		this.position.set(source);
		this.velocity.set(worldX, worldY);
	}
	public void update(float delta) {
		position.add(velocity.x * delta, velocity.y * delta);
	}
	
}
