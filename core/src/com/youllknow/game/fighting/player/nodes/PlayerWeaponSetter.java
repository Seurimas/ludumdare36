package com.youllknow.game.fighting.player.nodes;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;
import com.youllknow.game.fighting.player.PlayerWeapon;
import com.youllknow.game.wiring.Schematic.Wire;

public class PlayerWeaponSetter extends PlayerStatSetter {
	public enum WeaponStat {
		SHUT_OFF,
		FIRE_CHARGE,
		DRAIN_SHIELD
	};
	private final WeaponStat statType;
	public PlayerWeaponSetter(WeaponStat statType, Entity player) {
		super(player);
		this.statType = statType;
	}

	@Override
	public void handleInput(Wire wire, Energy energy) {
		PlayerWeapon weapon = player.getComponent(PlayerWeapon.class);
		switch (statType) {
		case DRAIN_SHIELD:
			weapon.drainEnergy = energy;
			break;
		case FIRE_CHARGE:
			weapon.fireChargeEnergy = energy;
			break;
		case SHUT_OFF:
			weapon.shutOffEnergy = energy;
			break;
		default:
			break;
			
		}
	}

}
