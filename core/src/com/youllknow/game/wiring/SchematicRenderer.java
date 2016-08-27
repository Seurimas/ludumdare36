package com.youllknow.game.wiring;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SchematicRenderer extends EntitySystem {
	private final Family entityFamily = Family.all(SchematicPopup.class).get();
	private final Batch uiBatch;
	private final ShapeRenderer uiShapes;
	public SchematicRenderer(ShapeRenderer uiShapes, Batch uiBatch) {
		this.uiBatch = uiBatch;
		this.uiShapes = uiShapes;
	}
	@Override
	public void update(float deltaTime) {
		for (Entity entity : getEngine().getEntitiesFor(entityFamily)) {
			SchematicPopup popup = entity.getComponent(SchematicPopup.class);
			popup.render(uiShapes, uiBatch, deltaTime);
		}
	}
}
