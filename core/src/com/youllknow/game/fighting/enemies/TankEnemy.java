package com.youllknow.game.fighting.enemies;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.HealthComponent.DamageType;
import com.youllknow.game.fighting.HealthComponent.DamageStrength;
import com.youllknow.game.fighting.player.AttachedWeapon;
import com.youllknow.game.fighting.projectiles.ProjectileWeapon;
import com.youllknow.game.fighting.projectiles.behaviors.SingleShotBehavior;

public class TankEnemy implements Component {
	private static TextureRegion sprite;
	private static Sound fire;
	private static final int WIDTH = 32;
	private static final float COOLDOWN = 1f;
	private float sinceLastShot = -1;
	public final Entity player;
	public TankEnemy(Entity player) {
		this.player = player;
	}
	public static void spawn(Engine engine, Entity player, Vector2 position) {
		Entity tank = new Entity();
		tank.add(new WorldDenizen(new Rectangle(position.x - WIDTH / 2, position.y, 32, 32), 50));
		tank.add(new TankEnemy(player));
		tank.add(new AttachedWeapon(tank, -10, -3));
		DamageType damageType = DamageType.EXPLOSIVE;
		int damageStrength = 6;
		tank.add(new ProjectileWeapon(tank, 400, new SingleShotBehavior(damageType, damageStrength), new PlayerOnlyTargetBehaviors(),
				fire, damageType.color, DamageStrength.color(damageStrength)));
		tank.add(new HealthComponent(10, new ExplosionDeathBehavior()));
		tank.add(new SimpleSpriteRenderer(sprite));
		engine.addEntity(tank);
	}
	public void fire(Engine engine, Entity entity, Vector2 center) {
		sinceLastShot = 0;
		ProjectileWeapon weapon = entity.getComponent(ProjectileWeapon.class);
		weapon.fire(engine, entity, center.x, center.y);
	}
	public void update(float delta) {
		sinceLastShot += delta;
	}
	public boolean canFire() {
		return sinceLastShot >= COOLDOWN;
	}
	public static void setSprite(TextureRegion sprite) {
		TankEnemy.sprite = sprite;
	}
	public static void setFireSound(Sound sound) {
		TankEnemy.fire = sound;
	}
}
