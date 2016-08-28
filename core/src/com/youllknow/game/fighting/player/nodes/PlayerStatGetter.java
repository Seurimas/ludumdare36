package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;

public abstract class PlayerStatGetter implements GameStateGetter {
	protected final Entity player;
	public PlayerStatGetter(Entity player) {
		this.player = player;
	}
}
