package com.youllknow.game.fighting.enemies;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.HealthComponent.DamageType;
import com.youllknow.game.fighting.HealthComponent.DamageStrength;
import com.youllknow.game.fighting.player.AttachedWeapon;
import com.youllknow.game.fighting.projectiles.ProjectileWeapon;
import com.youllknow.game.fighting.projectiles.behaviors.SingleShotBehavior;

public class SiloEnemy extends AttachedWeapon {
	private static TextureRegion sprite;
	private static Sound fire;
	private static final int WIDTH = 32;
	private static final float COOLDOWN = 1f;
	private float sinceLastShot = -1;
	public final Entity player;
	private final ProjectileWeapon energyWeapon, missileWeapon;
	public SiloEnemy(Entity player, Entity me) {
		super(me, 0, 4);
		this.player = player;
		this.energyWeapon = new ProjectileWeapon(me, 1000,
				new SingleShotBehavior(DamageType.ENERGY, 9), new PlayerOnlyTargetBehaviors(),
				fire, DamageType.ENERGY.color, DamageStrength.color(9));
		this.missileWeapon = new ProjectileWeapon(me, 1000, 
				new SingleShotBehavior(DamageType.EXPLOSIVE, 15), new PlayerOnlyTargetBehaviors(),
				fire, DamageType.EXPLOSIVE.color, DamageStrength.color(15));
	}
	public static void spawn(Engine engine, Entity player, Vector2 position) {
		Entity tank = new Entity();
		tank.add(new WorldDenizen(new Rectangle(position.x - WIDTH / 2, position.y, 32, 32), 50));
		tank.add(new SiloEnemy(player, tank));
		tank.add(new HealthComponent(10, new ExplosionDeathBehavior()));
		tank.add(new SimpleSpriteRenderer(sprite));
		engine.addEntity(tank);
	}
	public void fire(Engine engine, Entity entity, Vector2 center) {
		sinceLastShot = 0;
		if (MathUtils.randomBoolean())
			energyWeapon.fire(engine, entity, center.x, center.y);
		else
			missileWeapon.fire(engine, entity, center.x, center.y);
	}
	public void update(float delta) {
		sinceLastShot += delta;
	}
	public boolean canFire() {
		return sinceLastShot >= COOLDOWN;
	}
	public static void setSprite(TextureRegion sprite) {
		SiloEnemy.sprite = sprite;
	}
	public static void setFireSound(Sound sound) {
		SiloEnemy.fire = sound;
	}
	@Override
	public void moveSource(ProjectileWeapon weapon) {
		super.moveSource(energyWeapon);
		super.moveSource(missileWeapon);
	}
}
