package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.projectiles.Projectile.TargetBehavior;
import com.youllknow.game.fighting.projectiles.rendering.ColorCodedProjectileRenderer;
import com.youllknow.game.fighting.projectiles.rendering.ProjectileRendererComponent;

public class ProjectileWeapon implements Component {
	public static interface TargetBehaviorFactory {
		public TargetBehavior getBehavior(Entity shooter);
	}
	private final float speed;
	public final Vector2 source = new Vector2();
	private final Entity owner;
	private final Sound sound;
	private final Projectile.HitBehavior behavior;
	private final TargetBehaviorFactory targetFactory;
	private final ProjectileRendererComponent renderer;
	public ProjectileWeapon(Entity owner, float speed, Projectile.HitBehavior projectileBehavior,
			TargetBehaviorFactory targetFactory, Sound sound, Color shell, Color payload) {
		this.owner = owner;
		this.speed = speed;
		this.behavior = projectileBehavior;
		this.sound = sound;
		this.targetFactory = targetFactory;
		this.renderer = new ColorCodedProjectileRenderer(shell, payload);
	}
	private static final Vector2 temp = new Vector2();
	public void fire(Engine engine, Entity entity, float worldX, float worldY) {
		Entity dummy = new Entity();
		temp.set(worldX - source.x, worldY - source.y);
		temp.setLength(speed);
		dummy.add(new Projectile(owner, source, temp, behavior, targetFactory.getBehavior(owner)));
		dummy.add(renderer);
		engine.addEntity(dummy);
		sound.play();
	}

}
