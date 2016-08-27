package com.youllknow.game.ancient;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.youllknow.game.wiring.nodes.RegisteredEnergyNode;

public class GameStateEnergyInputNode extends RegisteredEnergyNode {
	public static interface GameStateSetter {
		public void handleInput(Energy energy);
	}
	private final TextureRegion sprite;
	private final GameStateSetter gameState;
	public GameStateEnergyInputNode(TextureRegion sprite, GameStateSetter gameState) {
		this.sprite = sprite;
		this.gameState = gameState;
	}
	@Override
	public TextureRegion getSprite() {
		return sprite;
	}

	@Override
	public Energy getOutput() {
		return null;
	}

	@Override
	public void handleInput(Energy input1) {
		gameState.handleInput(input1);
	}

	@Override
	public Energy getOutput(Energy input1, Energy input2) {
		return null;
	}

}
