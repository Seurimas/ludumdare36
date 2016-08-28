package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.youllknow.game.MainGameScreen;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.utils.AshleyUtils.ComponentSystem;

public class PlayerAdvancementSystem extends ComponentSystem<PlayerComponent> {

	public PlayerAdvancementSystem() {
		super(PlayerComponent.class);
	}
	@Override
	public void update(Engine engine, Entity entity, PlayerComponent mainComponent, float delta) {
		WorldDenizen denizen = entity.getComponent(WorldDenizen.class);
		if (denizen.getX() > mainComponent.screenLeft + 600)
			delta *= 2f;
		mainComponent.screenLeft += 50 * delta;
		boundPlayer(mainComponent, denizen);
	}
	private void boundPlayer(PlayerComponent mainComponent, WorldDenizen denizen) {
		denizen.clamp(mainComponent.screenLeft, mainComponent.screenLeft + MainGameScreen.SCREEN_WIDTH,
				32, MainGameScreen.WORLD_HEIGHT - 32);
	}
}
