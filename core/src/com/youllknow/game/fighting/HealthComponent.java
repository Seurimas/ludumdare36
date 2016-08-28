package com.youllknow.game.fighting;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;

public class HealthComponent implements Component {
	public enum DamageType {
		EXPLOSIVE(Color.GREEN),
		PROJECTILE(Color.RED),
		ENERGY(Color.BLUE);
		public Color color;
		DamageType(Color color) {
			this.color = color;
		}
	}
	public enum DamageStrength {
		LOW(5, Color.BLUE),
		MEDIUM(15, Color.GREEN),
		HIGH(1000, Color.RED);
		float max;
		Color color;
		DamageStrength(float value, Color color) {
			this.max = value;
			this.color = color;
		}
		public static Color color(float value) {
			return forDamage(value).color;
		}
		public static DamageStrength forDamage(float value) {
			if (value <= LOW.max)
				return LOW;
			else if (value <= MEDIUM.max)
				return MEDIUM;
			else
				return HIGH;
		}
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
