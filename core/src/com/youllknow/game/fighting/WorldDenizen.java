package com.youllknow.game.fighting;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WorldDenizen implements Component {
	private final Rectangle aabb;
	private final Vector2 velocity = new Vector2();
	private float weight;
	public WorldDenizen(Rectangle aabb, float weight) {
		this.aabb = aabb;
		this.weight = weight;
	}
	public void updateLocation(float delta) {
		aabb.x += velocity.x * delta;
		aabb.y += velocity.y * delta;
	}
	public void applyImpulse(Vector2 impulse) {
		velocity.x += impulse.x / weight;
		velocity.y += impulse.y / weight;
	}
	public void applyGravity(Vector2 gravity) {
		velocity.x += gravity.x;
		velocity.y += gravity.y;
	}
	public void hitFloor(float y) {
		aabb.y = y;
		velocity.y = 0;
	}
	public void hitCeiling(float y) {
		aabb.y = y - aabb.height;
		velocity.y = 0;
	}
	public float getX() {
		return aabb.x;
	}
	public float getY() {
		return aabb.y;
	}
	public float getWidth() {
		return aabb.width;
	}
	public float getHeight() {
		return aabb.height;
	}
}
