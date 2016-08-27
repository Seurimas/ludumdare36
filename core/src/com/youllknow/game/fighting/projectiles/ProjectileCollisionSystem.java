package com.youllknow.game.fighting.projectiles;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.utils.AshleyUtils;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class ProjectileCollisionSystem extends ComponentSystem<Projectile> {
	private static final Family entityFamily = Family.all(WorldDenizen.class).get();
	private Iterable<WorldDenizen> walkers = AshleyUtils.getComponentIterable(this, entityFamily, WorldDenizen.class);
	
	public ProjectileCollisionSystem() {
		super(Projectile.class);
	}

	@Override
	public void update(Engine engine, Entity entity, Projectile projectile, float delta) {
		Vector2 start = new Vector2();
		Vector2 end = new Vector2();
		start.set(projectile.position);
		end.set(projectile.velocity).scl(delta).add(start);
		for (Entity target : getEngine().getEntitiesFor(entityFamily)) {
			WorldDenizen walker = target.getComponent(WorldDenizen.class);
			if (Intersector.intersectSegmentCircle(start, end, walker.getCenter(), walker.getSquareRadius())) {
				projectile.hit(engine, entity, target);
			}
		}
	}

}
