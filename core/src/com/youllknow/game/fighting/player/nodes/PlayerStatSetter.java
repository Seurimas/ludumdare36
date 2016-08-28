package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyInputNode.GameStateSetter;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;

public abstract class PlayerStatSetter implements GameStateSetter {
	protected final Entity player;
	public PlayerStatSetter(Entity player) {
		this.player = player;
	}
}
