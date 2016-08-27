package com.youllknow.game.utils;

import java.util.Iterator;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;

public class AshleyUtils {
	public static <T extends Component> Iterable<T> getComponentIterable(final EntitySystem system, final Family family, final Class<T> componentClass) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					Iterator<Entity> baseIterator = system.getEngine().getEntitiesFor(family).iterator();

					@Override
					public boolean hasNext() {
						return baseIterator.hasNext();
					}

					@Override
					public T next() {
						return baseIterator.next().getComponent(componentClass);
					}
				};
			}
		};
	}
	public static interface ComponentHandler<T extends Component> {
		public void update(Engine engine, Entity entity, T mainComponent, float delta);
	}
	public static class ComponentSubSystem<T extends Component> {
		private final Class<T> componentClass;
		private final Family entityFamily;
		private final ComponentHandler<T> handler;
		public ComponentSubSystem(Family entityFamily, Class<T> componentClass, ComponentHandler<T> handler) {
			this.entityFamily = entityFamily;
			this.componentClass = componentClass;
			this.handler = handler;
		}
		public ComponentSubSystem(Class<T> componentClass, ComponentHandler<T> handler) {
			this(Family.all(componentClass).get(), componentClass, handler);
		}
		public void update(EntitySystem system, float deltaTime) {
			update(system.getEngine(), deltaTime);
		}
		public void update(Engine engine, float deltaTime) {
			for (Entity entity : engine.getEntitiesFor(entityFamily)) {
				handler.update(engine, entity, entity.getComponent(componentClass), deltaTime);
			}
		}
	}
}
