package com.youllknow.game.wiring.nodes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.youllknow.game.wiring.Schematic;
import com.youllknow.game.wiring.Schematic.EnergyNode;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;
import com.youllknow.game.wiring.Schematic.Wire;

public class LogicEnergyNode extends RegisteredEnergyNode {
	public static interface Logic {
		public Energy getOutput(Energy input1, Energy input2);
	}
	public static final Logic sameOrNeither = new Logic() {
		public Energy getOutput(Energy input1, Energy input2) {
			if (input1 == input2)
				return input1;
			else if (input1 == Energy.RED || input2 == Energy.RED) {
				if (input1 == Energy.BLUE || input2 == Energy.BLUE)
					return Energy.GREEN;
				else
					return Energy.BLUE;
			} else if (input1 == Energy.BLUE || input2 == Energy.BLUE) {
				return Energy.RED;
			}
			return input2;
		};
	};
	private Logic logic;
	public LogicEnergyNode(Logic logic) {
		this.logic = logic;
	}
	@Override
	public Energy getOutput(Energy input1, Energy input2) {
		return logic.getOutput(input1, input2);
	}

	@Override
	public TextureRegion getSprite() {
		return sprite;
	}
	private static TextureRegion sprite;
	public static void setSprite(TextureRegion region) {
		sprite = region;
	}

	@Override
	public Energy getOutput() {
		return null;
	}

	@Override
	public void handleInput(Wire wire, Energy input1) {
	}
}
