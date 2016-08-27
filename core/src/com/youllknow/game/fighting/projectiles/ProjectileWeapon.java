package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;

public class ProjectileWeapon implements Component {
	public final Vector2 source = new Vector2();
	private final Entity owner;
	private final Projectile.Behavior behavior;
	public ProjectileWeapon(Entity owner, Projectile.Behavior projectileBehavior) {
		this.owner = owner;
		this.behavior = projectileBehavior;
	}
	private static final Vector2 temp = new Vector2();
	public void fire(Engine engine, Entity entity, float worldX, float worldY) {
		Entity dummy = new Entity();
		temp.set(worldX - source.x, worldY - source.y);
		temp.setLength(2000);
		dummy.add(new Projectile(owner, source, temp, behavior));
		engine.addEntity(dummy);
	}

}
