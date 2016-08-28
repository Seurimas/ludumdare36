package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class HealthGetter extends PlayerStatGetter {
	
	public HealthGetter(Entity player) {
		super(player);
	}

	@Override
	public Energy getValue() {
		int healthClass = (int)(player.getComponent(HealthComponent.class).getHealth() / 30);
		if (healthClass <= 1) {
			return Energy.RED;
		} else if (healthClass <= 2) {
			return Energy.GREEN;
		} else {
			return Energy.BLUE;
		}
	}
	@Override
	public String getDescription() {
		return "Red for low health. Green for medium health. Blue for high health.";
	}
}
