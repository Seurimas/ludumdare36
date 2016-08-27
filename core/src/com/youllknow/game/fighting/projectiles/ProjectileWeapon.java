package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;

public class ProjectileWeapon implements Component {
	public final Vector2 source = new Vector2();
	public ProjectileWeapon() {
	}
	private static final Vector2 temp = new Vector2();
	public void fire(Engine engine, Entity entity, float worldX, float worldY) {
		Entity dummy = new Entity();
		temp.set(worldX - source.x, worldY - source.y);
		temp.setLength(2000);
		dummy.add(new Projectile(source, temp));
		engine.addEntity(dummy);
	}

}
