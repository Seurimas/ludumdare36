package com.youllknow.game.wiring.nodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.youllknow.game.IconManager;
import com.youllknow.game.wiring.Schematic;
import com.youllknow.game.wiring.Schematic.EnergyNode;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;
import com.youllknow.game.wiring.Schematic.Wire;

public class LogicEnergyNode extends RegisteredEnergyNode {
	public static interface Logic {
		public Energy getOutput(Energy input1, Energy input2);
		public Color getColor();
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
		public Color getColor() {
			return Color.WHITE;
		}
	};
	public static final Logic oddBlue = new Logic() {
		public Energy getOutput(Energy input1, Energy input2) {
			if (input1 == input2)
				return Energy.RED;
			else
				return Energy.BLUE;
		};
		public Color getColor() {
			return Color.PURPLE;
		}
	};
	public static final Logic oddRed = new Logic() {
		public Energy getOutput(Energy input1, Energy input2) {
			if (input1 == input2)
				return Energy.GREEN;
			else
				return Energy.RED;
		};
		public Color getColor() {
			return Color.YELLOW;
		}
	};
	public static final Logic oddGreen = new Logic() {
		public Energy getOutput(Energy input1, Energy input2) {
			if (input1 == input2)
				return Energy.BLUE;
			else
				return Energy.GREEN;
		};
		public Color getColor() {
			return Color.CYAN;
		}
	};
	public static final Logic red = new Logic() {
		public Energy getOutput(Energy input1, Energy input2) {
			return Energy.RED;
		};
		public Color getColor() {
			return Color.RED;
		}
	};
	public static final Logic green = new Logic() {
		public Energy getOutput(Energy input1, Energy input2) {
			return Energy.GREEN;
		};
		public Color getColor() {
			return Color.GREEN;
		}
	};
	public static final Logic blue = new Logic() {
		public Energy getOutput(Energy input1, Energy input2) {
			return Energy.BLUE;
		};
		public Color getColor() {
			return Color.BLUE;
		}
	};
	private static final Logic[] logics = new Logic[] {
			sameOrNeither,
			oddBlue,
			oddGreen,
			oddRed,
			blue,
			green,
			red
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
		return IconManager.getLogicIcon();
	}

	@Override
	public Energy getOutput() {
		return null;
	}

	@Override
	public void handleInput(Wire wire, Energy input1) {
	}
	@Override
	public Color getColor() {
		return logic.getColor();
	}
	public void toggleLogic() {
		for (int i = 0;i < logics.length - 1;i++) {
			if (logic == logics[i]) {
				logic = logics[i + 1];
				return;
			}
		}
		logic = logics[0];
	}
}
