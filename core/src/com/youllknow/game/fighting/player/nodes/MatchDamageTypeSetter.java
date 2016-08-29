package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyInputNode.GameStateSetter;
import com.youllknow.game.fighting.player.PlayerComponent;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;
import com.youllknow.game.wiring.Schematic.Wire;

public class MatchDamageTypeSetter extends PlayerStatSetter implements GameStateSetter {
	private boolean triggered;
	public MatchDamageTypeSetter(Entity player) {
		super(player);
	}

	@Override
	public void handleInput(Wire wire, Energy energy) {
		if (energy.equals(DamageTypeGetter.getDamageTypeEnergy(player))) {
			player.getComponent(PlayerComponent.class).shieldUp(0.125f);
			triggered = true;
		} else
			triggered = false;
	}

	@Override
	public String getDescription() {
		return "If the color matches the output of the Damage Type node, gain 1/8 shield power.";
	}

	@Override
	public boolean needsAttention() {
		return triggered;
	}

}
