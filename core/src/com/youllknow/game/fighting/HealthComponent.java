package com.youllknow.game.fighting;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
	private float maxHealth;
	private float currentHealth;
	public HealthComponent(float maxHealth) {
		this.maxHealth = maxHealth;
		currentHealth = maxHealth;
	}
	public void damage(float damage) {
		currentHealth -= damage;
		System.out.println(currentHealth);
	}

}
