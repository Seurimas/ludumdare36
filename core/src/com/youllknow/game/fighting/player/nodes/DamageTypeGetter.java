package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class DamageTypeGetter extends PlayerStatGetter {
	public DamageTypeGetter(Entity player) {
		super(player);
	}

	@Override
	public Energy getValue() {
		switch (player.getComponent(HealthComponent.class).getLastDamageType()) {
		case ENERGY:
			return Energy.BLUE;
		case EXPLOSIVE:
			return Energy.RED;
		case PROJECTILE:
			return Energy.GREEN;
		default:
			return Energy.GREEN;
		}
	}

}
