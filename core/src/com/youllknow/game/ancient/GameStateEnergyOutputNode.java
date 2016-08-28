package com.youllknow.game.ancient;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.youllknow.game.wiring.Schematic.Wire;
import com.youllknow.game.wiring.nodes.RegisteredEnergyNode;

public class GameStateEnergyOutputNode extends RegisteredEnergyNode {
	public static interface GameStateGetter {
		public Energy getValue();
		public String getDescription();
	}
	private final Color color;
	private final TextureRegion sprite;
	private final GameStateGetter gameState;
	public GameStateEnergyOutputNode(Color color, TextureRegion sprite, GameStateGetter gameState) {
		this.color = color;
		this.sprite = sprite;
		this.gameState = gameState;
	}
	@Override
	public TextureRegion getSprite() {
		return sprite;
	}

	@Override
	public Energy getOutput() {
		return gameState.getValue();
	}

	@Override
	public void handleInput(Wire wire, Energy input1) {
	}

	@Override
	public Energy getOutput(Energy input1, Energy input2) {
		return null;
	}
	@Override
	public Color getColor() {
		return color;
	}
	@Override
	public String getTooltip() {
		return String.format("This node outputs energy as follows: %s", gameState.getDescription());
	}

}
