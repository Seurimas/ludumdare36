package com.youllknow.game.fighting.rendering;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.youllknow.game.fighting.PlayerComponent;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.utils.AshleyUtils;

public class DebugWorldRenderer extends EntitySystem {
	private static final Family entityFamily = Family.all(WorldDenizen.class).get();
	private Iterable<WorldDenizen> walkers = AshleyUtils.getComponentIterable(this, entityFamily, WorldDenizen.class);
	private final Batch batch;
	private final ShapeRenderer shapes;
	public DebugWorldRenderer(Batch batch, ShapeRenderer shapes) {
		this.batch = batch;
		this.shapes = shapes;
	}
	@Override
	public void update(float deltaTime) {
		shapes.begin(ShapeType.Filled);
		shapes.setColor(Color.RED);
		for (WorldDenizen walker : walkers) {
			shapes.rect(walker.getX(), walker.getY(), walker.getWidth(), walker.getHeight());
		}
		shapes.end();
	}
}
