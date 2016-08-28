package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;
import com.youllknow.game.fighting.player.PlayerComponent;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class HeatGetter extends PlayerStatGetter {
	public HeatGetter(Entity player) {
		super(player);
	}

	@Override
	public Energy getValue() {
		return getHeatEnergy(player);
	}

	public static Energy getHeatEnergy(Entity entity) {
		float heat = entity.getComponent(PlayerComponent.class).getHeatLevel();
		if (heat < 0.5)
			return Energy.BLUE;
		else if (heat < 1)
			return Energy.GREEN;
		else
			return Energy.RED;
	}

}
