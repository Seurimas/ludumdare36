package com.youllknow.game.fighting.projectiles.rendering;

import java.util.HashSet;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.projectiles.Projectile;
import com.youllknow.game.fighting.rendering.DenizenRendererComponent;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class ProjectileRenderer  extends ComponentSystem<Projectile> {
	public static final HashSet<Class<? extends ProjectileRendererComponent>> renderers = new HashSet<Class<? extends ProjectileRendererComponent>>();
	public static void addRenderer(Class<? extends ProjectileRendererComponent> rendererClass) {
		renderers.add(rendererClass);
	}
	private final Batch batch;
	public ProjectileRenderer(Batch batch) {
		super(Projectile.class);
		this.batch = batch;
	}
	@Override
	public void update(Engine engine, Entity entity, Projectile mainComponent, float delta) {
		for (Class<? extends ProjectileRendererComponent> renderer : renderers) {
			ProjectileRendererComponent rendererComponent = entity.getComponent(renderer);
			if (rendererComponent != null)
				rendererComponent.draw(batch, entity, mainComponent.position, mainComponent.velocity.angle(), delta);
		}
	}
	@Override
	public void update(float deltaTime) {
		batch.begin();
		super.update(deltaTime);
		batch.end();
	}
}
