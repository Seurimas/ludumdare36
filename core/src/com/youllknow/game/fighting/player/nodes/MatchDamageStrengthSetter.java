package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyInputNode.GameStateSetter;
import com.youllknow.game.fighting.player.PlayerComponent;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;
import com.youllknow.game.wiring.Schematic.Wire;

public class MatchDamageStrengthSetter extends PlayerStatSetter implements GameStateSetter {

	public MatchDamageStrengthSetter(Entity player) {
		super(player);
	}

	@Override
	public void handleInput(Wire wire, Energy energy) {
		if (energy.equals(DamageStrengthGetter.getDamageStrengthEnergy(player))) {
			player.getComponent(PlayerComponent.class).shieldUp(0.125f);
		}
	}

}
