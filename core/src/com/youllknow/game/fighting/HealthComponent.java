package com.youllknow.game.fighting;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class HealthComponent implements Component {
	public static interface DeathBehavior {
		public void onDeath(Engine engine, Entity entity);
	}
	private float maxHealth;
	private final DeathBehavior behavior;
	private float currentHealth;
	public HealthComponent(float maxHealth, DeathBehavior behavior) {
		this.maxHealth = maxHealth;
		currentHealth = maxHealth;
		this.behavior = behavior;
	}
	public void damage(float damage) {
		currentHealth -= damage;
		System.out.println(currentHealth);
	}
	public float getHealth() {
		return currentHealth;
	}
	public void handleDeath(Engine engine, Entity entity) {
		behavior.onDeath(engine, entity);
	}

}
