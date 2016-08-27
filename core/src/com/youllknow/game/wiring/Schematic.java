package com.youllknow.game.wiring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.wiring.Schematic.EnergyNode;

public class Schematic {
	public static interface EnergyNode {
		public enum Energy {
			RED,
			GREEN,
			BLUE
		};
		public int getId();
		public TextureRegion getSprite();
		public Energy getOutput(Energy input1, Energy input2);
	}
	public static class Wire {
		public final EnergyNode input, output;
		public Wire(EnergyNode input, EnergyNode output) {
			this.input = input;
			this.output = output;
		}
	}
	private HashSet<EnergyNode> nodes = new HashSet<EnergyNode>();
	private HashMap<EnergyNode, List<Wire>> wiring = new HashMap<EnergyNode, List<Wire>>();
	private HashSet<Wire> wires = new HashSet<Wire>();
	private final HashMap<EnergyNode, Vector2> diagram = new HashMap<EnergyNode, Vector2>();
	public Schematic() {
	}
	public void addNode(EnergyNode node) {
		nodes.add(node);
		wiring.put(node, new ArrayList<Wire>());
	}
	public void addWire(EnergyNode input, EnergyNode output) {
		assert hasNode(input);
		assert hasNode(output);
		Wire wire = new Wire(input, output);
		wiring.get(input).add(wire);
		wires.add(wire);
	}
	public boolean hasNode(EnergyNode node) {
		return nodes.contains(node);
	}
	public void setLocation(EnergyNode node, float x, float y) {
		assert hasNode(node);
		diagram.put(node, new Vector2(x, y));
	}
	public Vector2 getLocation(EnergyNode node) {
		return diagram.get(node);
	}
	public Collection<EnergyNode> getNodes() {
		return diagram.keySet();
	}
	public Collection<Wire> getWires() {
		return wires;
	}
}
