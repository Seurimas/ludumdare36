package com.youllknow.game.fighting.enemies;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.fighting.PlayerComponent;
import com.youllknow.game.fighting.projectiles.Projectile.TargetBehavior;
import com.youllknow.game.fighting.projectiles.ProjectileWeapon.TargetBehaviorFactory;

public class PlayerOnlyTargetBehaviors implements TargetBehaviorFactory {

	@Override
	public TargetBehavior getBehavior(Entity shooter) {
		return new TargetBehavior() {
			
			@Override
			public boolean doesHit(Entity target) {
				return target.getComponent(PlayerComponent.class) != null;
			}
		};
	}

}
