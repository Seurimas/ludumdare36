package com.youllknow.game.wiring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.wiring.Schematic.EnergyNode;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;
import com.youllknow.game.wiring.Schematic.Wire;

public class Schematic {
	public static interface EnergyNode {
		public enum Energy {
			RED(new Color(1, 0, 0, 1), new Color(0.8f, 0, 0, 1), new Color(0.6f, 0, 0, 1)),
			GREEN(new Color(0, 1, 0, 1), new Color(0, 0.8f, 0, 1), new Color(0, 0.6f, 0, 1)),
			BLUE(new Color(0, 0, 1, 1), new Color(0, 0, 0.8f, 1), new Color(0, 0, 0.6f, 1));
			public Color c1, c2, c3;
			Energy(Color c1, Color c2, Color c3) {
				this.c1 = c1;
				this.c2 = c2;
				this.c3 = c3;
			}
		};
		public int getId();
		public TextureRegion getSprite();
		public Energy getOutput();
		public Energy getOutput(Energy input1, Energy input2);
		public void handleInput(Wire wire, Energy input1);
	}
	public static class Wire {
		public final EnergyNode input, output;
		public Wire(EnergyNode input, EnergyNode output) {
			this.input = input;
			this.output = output;
		}
	}
	private HashSet<EnergyNode> nodes = new HashSet<EnergyNode>();
	private HashSet<EnergyNode> inputs = new HashSet<EnergyNode>();
	private HashSet<EnergyNode> outputs = new HashSet<EnergyNode>();
	private HashMap<EnergyNode, List<Wire>> wiring = new HashMap<EnergyNode, List<Wire>>();
	private HashMap<EnergyNode, List<Wire>> dependencies = new HashMap<EnergyNode, List<Wire>>();
	private HashSet<Wire> wires = new HashSet<Wire>();
	private final HashMap<EnergyNode, Vector2> diagram = new HashMap<EnergyNode, Vector2>();
	public Schematic() {
	}
	public void addNode(EnergyNode node) {
		nodes.add(node);
		wiring.put(node, new ArrayList<Wire>());
		dependencies.put(node, new ArrayList<Wire>());
	}
	public void addWire(EnergyNode input, EnergyNode output) {
		assert hasNode(input);
		assert hasNode(output);
		Wire wire = new Wire(input, output);
		wiring.get(input).add(wire);
		dependencies.get(output).add(wire);
		wires.add(wire);
		inputs.add(output);
		outputs.add(input);
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
		return new HashSet<EnergyNode>(diagram.keySet());
	}
	public Collection<Wire> getWires() {
		return wires;
	}
	public List<Wire> getDependencies(EnergyNode node) {
		return dependencies.get(node);
	}
	public List<Wire> getOutflows(EnergyNode node) {
		return wiring.get(node);
	}
	public Collection<EnergyNode> getInputs() {
		return inputs;
	}
}
