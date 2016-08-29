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

	@Override
	public String getDescription() {
		switch (statType) {
		case DRAIN_SHIELD:
			return "Blue energy drains the shield for extra power. Red energy diverts power to shields.";
		case FIRE_CHARGE:
			return "Green energy diverts power to reserves. Red energy takes power from reserves.";
		case SHUT_OFF:
			return "Red energy stops weapons from firing. Blue energy is heat efficient.";
		default:
			return "???";
		}
	}
	@Override
	public boolean needsAttention() {
		PlayerWeapon weapon = player.getComponent(PlayerWeapon.class);
		switch (statType) {
		case DRAIN_SHIELD:
			return weapon.drainEnergy.equals(Energy.RED);
		case FIRE_CHARGE:
			return weapon.fireChargeEnergy.equals(Energy.GREEN);
		case SHUT_OFF:
			return weapon.shutOffEnergy.equals(Energy.RED);
		default:
			return false;
		}
	}
}
