package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.projectiles.ProjectileWeapon;

public class AttachedWeapon implements Component {
	private final Entity player;
	private final float xOffset, yOffset;
	public AttachedWeapon(Entity player, float xOffset, float yOffset) {
		this.player = player;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	public void moveSource(ProjectileWeapon weapon) {
		WorldDenizen denizen = player.getComponent(WorldDenizen.class);
		weapon.source.set(denizen.getCenter());
		weapon.source.add(xOffset, yOffset);
	}

}
