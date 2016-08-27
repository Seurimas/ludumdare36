package com.youllknow.game.fighting.input;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.youllknow.game.fighting.WorldDenizen;

public class ProjectileWeapon implements Component {
	
	public void fire(Engine engine, Entity entity, float worldX, float worldY) {
		Entity dummy = new Entity();
		dummy.add(new WorldDenizen(new Rectangle(worldX, worldY, 5, 5), 1));
		engine.addEntity(dummy);
	}

}
