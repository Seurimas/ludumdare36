package com.youllknow.game.wiring.nodes;

import com.youllknow.game.wiring.Schematic.EnergyNode;

public abstract class RegisteredEnergyNode implements EnergyNode {
	private final int id;
	private static int nextId = 0;
	public RegisteredEnergyNode() {
		this.id = nextId++;
	}
	@Override
	public int getId() {
		return id;
	}
}
