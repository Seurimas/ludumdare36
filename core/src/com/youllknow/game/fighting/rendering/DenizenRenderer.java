package com.youllknow.game.fighting.rendering;

import java.util.HashSet;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class DenizenRenderer extends ComponentSystem<WorldDenizen> {
	public static final HashSet<Class<? extends DenizenRendererComponent>> renderers = new HashSet<Class<? extends DenizenRendererComponent>>();
	public static void addRenderer(Class<? extends DenizenRendererComponent> rendererClass) {
		renderers.add(rendererClass);
	}
	private final Batch batch;
	public DenizenRenderer(Batch batch) {
		super(WorldDenizen.class);
		this.batch = batch;
	}

	@Override
	public void update(Engine engine, Entity entity, WorldDenizen walker, float delta) {
		batch.begin();
		for (Class<? extends DenizenRendererComponent> renderer : renderers) {
			DenizenRendererComponent rendererComponent = entity.getComponent(renderer);
			if (rendererComponent != null)
				rendererComponent.draw(batch, entity, walker.getBounds(), delta);
		}
		batch.end();
	}

}
