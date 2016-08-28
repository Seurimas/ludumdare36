package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class WeaponChargeGetter extends PlayerStatGetter {
	public WeaponChargeGetter(Entity player) {
		super(player);
	}

	@Override
	public Energy getValue() {
		return null;
	}

}
