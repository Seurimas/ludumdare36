package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyInputNode.GameStateSetter;
import com.youllknow.game.fighting.player.PlayerComponent;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;
import com.youllknow.game.wiring.Schematic.Wire;

public class MatchHeatSetter extends PlayerStatSetter implements GameStateSetter {
	private boolean triggered;
	public MatchHeatSetter(Entity player) {
		super(player);
	}

	@Override
	public void handleInput(Wire wire, Energy energy) {
		if (energy.equals(HeatGetter.getHeatEnergy(player))) {
			player.getComponent(PlayerComponent.class).heatUp(0.025f);
			player.getComponent(PlayerComponent.class).shieldUp(0.25f);
			player.getComponent(PlayerComponent.class).updateShield(0.5f);
			triggered = true;
		} else
			triggered = false;
	}

	@Override
	public String getDescription() {
		return "If the color matches the output of the Heat node, gain shield, generate heat, and trigger shield.";
	}
	@Override
	public boolean needsAttention() {
		return triggered;
	}
}
