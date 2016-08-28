package com.youllknow.game.utils;

import com.youllknow.game.wiring.Schematic.EnergyNode;

public class TooltipManager {
	public String tooltip = null;
	public void setForNode(EnergyNode node) {
		if (node != null) {
			tooltip = node.getTooltip();
		} else {
			tooltip = null;
		}
	}
}
