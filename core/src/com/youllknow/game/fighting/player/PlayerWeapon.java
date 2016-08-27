package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;

public class PlayerWeapon implements Component {
	private final Entity player;
	private final float xOffset, yOffset;
	public PlayerWeapon(Entity player, float xOffset, float yOffset) {
		this.player = player;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	public void moveSource(Vector2 source) {
		WorldDenizen denizen = player.getComponent(WorldDenizen.class);
		source.x = denizen.getX() + xOffset;
		source.y = denizen.getY() + yOffset;
	}

}
