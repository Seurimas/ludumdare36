package com.youllknow.game.wiring;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.youllknow.game.wiring.Schematic.EnergyNode;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;
import com.youllknow.game.wiring.Schematic.Wire;

public class PowerFlow {
	private final Schematic schematic;
	private final HashMap<Wire, Energy> flows = new HashMap<Wire, Energy>();
	public PowerFlow(Schematic schematic) {
		this.schematic = schematic;
	}
	public void calculate() {
		flows.clear();
		Collection<EnergyNode> unresolved = schematic.getNodes();
		while (!unresolved.isEmpty()) {
			for (EnergyNode node : unresolved) {
				if (tryToResolve(node)) {
					unresolved.remove(node);
					break;
				}
			}
		}
		for (Wire wire : flows.keySet()) {
			wire.output.handleInput(wire, flows.get(wire));
		}
	}
	private boolean tryToResolve(EnergyNode node) {
		List<Wire> wires = schematic.getDependencies(node);
		for (Wire wire : wires) {
			if (!flows.containsKey(wire))
				return false; // Can't resolve, we're missing a flow.
		}
		if (wires.size() == 2) {
			resolve(node, wires.get(0), wires.get(1));
		} else {
			resolve(node, node.getOutput());
		}
		return true;
	}
	private void resolve(EnergyNode node, Wire wire1, Wire wire2) {
		Energy result = node.getOutput(flows.get(wire1), flows.get(wire2));
		resolve(node, result);
	}
	private void resolve(EnergyNode node, Energy result) {
		for (Wire setWire : schematic.getOutflows(node)) {
			flows.put(setWire, result);
		}
	}
	public Energy getValue(Wire wire) {
		return flows.get(wire);
	}
}
