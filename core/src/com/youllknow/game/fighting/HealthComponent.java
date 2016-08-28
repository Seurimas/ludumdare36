package com.youllknow.game.fighting;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class HealthComponent implements Component {
	public enum DamageType {
		EXPLOSIVE,
		PROJECTILE,
		ENERGY;
	}
	public static interface DeathBehavior {
		public void onDeath(Engine engine, Entity entity);
	}
	private float maxHealth;
	private final DeathBehavior behavior;
	private float currentHealth;
	private DamageType lastDamageType = DamageType.ENERGY;
	private float lastDamage = 0;
	public HealthComponent(float maxHealth, DeathBehavior behavior) {
		this.maxHealth = maxHealth;
		currentHealth = maxHealth;
		this.behavior = behavior;
	}
	public void damage(DamageType type, float damage) {
		currentHealth -= damage;
		lastDamage = damage;
		lastDamageType = type;
	}
	public float getHealth() {
		return currentHealth;
	}
	public void handleDeath(Engine engine, Entity entity) {
		behavior.onDeath(engine, entity);
	}
	public DamageType getLastDamageType() {
		return lastDamageType;
	}
	public float getLastDamage() {
		return lastDamage;
	}
	public float getHealthPercent() {
		return currentHealth / maxHealth;
	}

}
