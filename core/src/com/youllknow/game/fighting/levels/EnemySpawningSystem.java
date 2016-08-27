package com.youllknow.game.fighting.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.enemies.TankEnemy;

public class EnemySpawningSystem extends EntitySystem {
	public static interface Level {
		public void spawn(Engine engine, Entity player, float currentTime, float x);
	}
	public static final Level tankLevel = new Level() {
		float lastSpawn = 0;
		float spawnInterval = 5;
		@Override
		public void spawn(Engine engine, Entity player, float currentTime, float x) {
			if (currentTime - lastSpawn > spawnInterval) {
				TankEnemy.spawn(engine, player, new Vector2(x + 1200, 0));
				lastSpawn = currentTime;
			}
		}
	};
	private final Entity player;
	private Level level = tankLevel;
	private float timeElapsed = 0;
	public EnemySpawningSystem(Entity player) {
		this.player = player;
	}
	@Override
	public void update(float deltaTime) {
		if (level == null)
			return;
		float playerLocation = player.getComponent(WorldDenizen.class).getX();
		timeElapsed += deltaTime;
		level.spawn(getEngine(), player, timeElapsed, playerLocation);
	}
}
