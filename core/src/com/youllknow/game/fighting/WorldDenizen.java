package com.youllknow.game.fighting;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WorldDenizen implements Component {
	private final Rectangle aabb;
	private final Vector2 velocity = new Vector2();
	private float weight;
	private final float maxSpeed;
	public WorldDenizen(Rectangle aabb, float weight) {
		this.aabb = aabb;
		this.weight = weight;
		maxSpeed = 5000 / weight;
	}
	public void updateLocation(float delta) {
		aabb.x += velocity.x * delta;
		aabb.y += velocity.y * delta;
	}
	public void applyImpulse(Vector2 impulse) {
		velocity.x += impulse.x / weight;
		velocity.y += impulse.y / weight;
		velocity.clamp(0, maxSpeed);
	}
	public void applyGravity(Vector2 gravity) {
		velocity.x += gravity.x;
		velocity.y += gravity.y;
		velocity.clamp(0, maxSpeed);
	}
	public void applyFriction(float friction) {
		if (velocity.x > friction)
			velocity.x -= friction;
		else if (velocity.x < -friction)
			velocity.x += friction;
		else
			velocity.x = 0;
		
		if (velocity.y > friction)
			velocity.y -= friction;
		else if (velocity.y < -friction)
			velocity.y += friction;
		else
			velocity.y = 0;
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
	private final Vector2 tempReturn = new Vector2();
	public Vector2 getCenter() {
		tempReturn.set(aabb.x + aabb.width / 2, aabb.y + aabb.height / 2);
		return tempReturn;
	}
	public float getSquareRadius() {
		float radius = Math.max(aabb.width, aabb.height) / 2;
		return radius * radius;
	}
}
