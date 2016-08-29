package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.youllknow.game.LudumDare36Game;
import com.youllknow.game.fighting.HealthComponent.DeathBehavior;

public class PlayerDeathBehavior implements DeathBehavior {
	private final LudumDare36Game game;
	public PlayerDeathBehavior(LudumDare36Game game) {
		this.game = game;
	}
	@Override
	public void onDeath(Engine engine, Entity entity) {
		game.gameOver();
	}

}
