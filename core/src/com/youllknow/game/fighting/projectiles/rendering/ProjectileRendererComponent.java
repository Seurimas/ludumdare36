package com.youllknow.game.fighting.projectiles.rendering;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface ProjectileRendererComponent extends Component {
	public void draw(Batch batch, Entity entity, Vector2 position, float rotation, float deltaTime);
}
