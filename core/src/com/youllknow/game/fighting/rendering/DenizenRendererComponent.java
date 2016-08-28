package com.youllknow.game.fighting.rendering;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public interface DenizenRendererComponent extends Component {
	public void draw(Batch batch, Entity entity, Rectangle area, float deltaTime);
}
