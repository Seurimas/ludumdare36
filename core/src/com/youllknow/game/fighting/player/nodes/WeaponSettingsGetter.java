package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;
import com.youllknow.game.fighting.player.PlayerComponent;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;

public class WeaponSettingsGetter extends PlayerStatGetter {
	public WeaponSettingsGetter(Entity player) {
		super(player);
	}

	@Override
	public Energy getValue() {
		return player.getComponent(PlayerComponent.class).weaponSettings;
	}
	@Override
	public String getDescription() {
		return "Outputs the set weapon energy.";
	}
}
