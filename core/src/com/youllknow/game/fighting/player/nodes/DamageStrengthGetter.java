package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class DamageStrengthGetter extends PlayerStatGetter {
	public DamageStrengthGetter(Entity player) {
		super(player);
	}
	@Override
	public Energy getValue() {
		return getDamageStrengthEnergy(player);
	}
	public static Energy getDamageStrengthEnergy(Entity entity) {
		int damageClass = (int)(entity.getComponent(HealthComponent.class).getLastDamage() / 5);
		if (damageClass <= 1) {
			return Energy.BLUE;
		} else if (damageClass <= 3) {
			return Energy.GREEN;
		} else {
			return Energy.RED;
		}
	}
	@Override
	public String getDescription() {
		return "Blue for weak attacks. Green for medium attacks. Red for strong attacks.";
	}

}
