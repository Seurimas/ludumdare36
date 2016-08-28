package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;
import com.youllknow.game.fighting.player.PlayerWeapon;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class WeaponChargeGetter extends PlayerStatGetter {
	public WeaponChargeGetter(Entity player) {
		super(player);
	}

	@Override
	public Energy getValue() {
		float charge = player.getComponent(PlayerWeapon.class).getCharge();
		if (charge < 0.5f)
			return Energy.BLUE;
		else if (charge < 1)
			return Energy.GREEN;
		else
			return Energy.RED;
	}
	@Override
	public String getDescription() {
		return "Blue for low reserves. Green for medium reserves. Red for high reserves.";
	}
}
