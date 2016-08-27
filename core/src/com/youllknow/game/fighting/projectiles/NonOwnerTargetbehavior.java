package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Entity;
import com.youllknow.game.fighting.projectiles.Projectile.TargetBehavior;
import com.youllknow.game.fighting.projectiles.ProjectileWeapon.TargetBehaviorFactory;

public class NonOwnerTargetbehavior implements TargetBehaviorFactory {

	@Override
	public TargetBehavior getBehavior(final Entity shooter) {
		return new TargetBehavior() {
			@Override
			public boolean doesHit(Entity target) {
				return target != shooter;
			}
		};
	}

}
