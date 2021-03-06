package com.youllknow.game.fighting.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.MainGameScreen;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.enemies.HelicopterEnemy;
import com.youllknow.game.fighting.enemies.SiloEnemy;
import com.youllknow.game.fighting.enemies.TankEnemy;
import com.youllknow.game.fighting.player.PlayerComponent;

public class EnemySpawningSystem extends EntitySystem {
	private static final float INTRO_LENGTH = 50 * 60;
	private static final class IntroLevel implements Level {

		@Override
		public boolean spawn(Engine engine, Entity player, float currentTime, float x) {
			return false;
		}

		@Override
		public boolean finished(float x) {
			return x > INTRO_LENGTH;
		}
		
	}
	private static final class HelicopterLevel implements Level {
		float lastSpawn = 0;
		float spawnInterval = 2;
		float maxSpawnInterval = 15;
		float finishedAt = INTRO_LENGTH + 2000;

		@Override
		public boolean spawn(Engine engine, Entity player, float currentTime, float x) {
			if (currentTime - lastSpawn > spawnInterval) {
				HelicopterEnemy.spawn(engine, player, new Vector2(x + MainGameScreen.SCREEN_WIDTH, 
						spawnInterval % 2 == 0 ? MainGameScreen.WORLD_HEIGHT - 32 : 0));
				lastSpawn = currentTime;
				spawnInterval++;
				if (spawnInterval >= maxSpawnInterval)
					spawnInterval = maxSpawnInterval - 3;
				return true;
			}
			return false;
		}

		@Override
		public boolean finished(float x) {
			return x > finishedAt;
		}

		public void levelUp() {
			maxSpawnInterval -= 3;
			if (maxSpawnInterval < 4)
				maxSpawnInterval = 4;
			finishedAt += 3000;
		}
	}
	private static final class TankLevel implements Level {
		float lastSpawn = 0;
		float spawnInterval = 2;
		float maxSpawnInterval = 12;
		float finishedAt = INTRO_LENGTH + 1000;

		@Override
		public boolean spawn(Engine engine, Entity player, float currentTime, float x) {
			if (currentTime - lastSpawn > spawnInterval) {
				TankEnemy.spawn(engine, player, new Vector2(x + MainGameScreen.SCREEN_WIDTH, 
						spawnInterval % 2 == 0 ? MainGameScreen.WORLD_HEIGHT - 32 : 0));
				lastSpawn = currentTime;
				spawnInterval++;
				if (spawnInterval >= maxSpawnInterval)
					spawnInterval = maxSpawnInterval - 3;
				return true;
			}
			return false;
		}

		@Override
		public boolean finished(float x) {
			return x > finishedAt;
		}

		public void levelUp() {
			maxSpawnInterval -= 3;
			if (maxSpawnInterval < 4)
				maxSpawnInterval = 4;
			finishedAt += 3000;
		}
	}
	public static interface Level {
		public boolean spawn(Engine engine, Entity player, float currentTime, float x);
		public boolean finished(float x);
	}
	public final IntroLevel introLevel = new IntroLevel();
	public final TankLevel tankLevel = new TankLevel();
	public final HelicopterLevel helicopterLevel = new HelicopterLevel();
	private final Entity player;
	private Level level = introLevel;
	private float timeElapsed = 0;
	int siloSpawnChance = 0;
	int siloSpawnMax = 5;
	public EnemySpawningSystem(Entity player) {
		this.player = player;
	}
	@Override
	public void update(float deltaTime) {
		if (level == null)
			return;
		float playerLocation = player.getComponent(PlayerComponent.class).screenLeft;
		timeElapsed += deltaTime;
		if (level.spawn(getEngine(), player, timeElapsed, playerLocation)) {
			if (MathUtils.random(siloSpawnMax) < siloSpawnChance) {
				SiloEnemy.spawn(getEngine(), player, new Vector2(playerLocation + MainGameScreen.SCREEN_WIDTH, 
						(MainGameScreen.WORLD_HEIGHT - 64) * MathUtils.random()));
			}
		}
		if (level.finished(playerLocation))
			advance();
	}
	private void advance() {
		if (level == introLevel)
			level = tankLevel;
		else if (level == tankLevel)
			level = helicopterLevel;
		else if (level == helicopterLevel) {
			tankLevel.levelUp();
			helicopterLevel.levelUp();
			level = tankLevel;
			siloSpawnChance++;
			if (siloSpawnChance >= siloSpawnMax)
				siloSpawnChance = siloSpawnMax - 1;
		}
	}
}
