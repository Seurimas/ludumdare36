package com.youllknow.game.fighting.player;

import com.youllknow.game.fighting.HealthComponent.DamageListener;
import com.youllknow.game.fighting.HealthComponent.DamageType;
import com.youllknow.game.wiring.PowerFlow;

public class ShieldDamageListener implements DamageListener {
	private final PowerFlow powerFlow;
	public ShieldDamageListener(PowerFlow powerFlow) {
		this.powerFlow = powerFlow;
	}
	@Override
	public boolean onDamage(DamageType type, float damage) {
		powerFlow.calculate();
		return false;
	}

}
