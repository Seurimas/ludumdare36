package com.youllknow.game.wiring;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.youllknow.game.wiring.Schematic.EnergyNode;
import com.youllknow.game.wiring.Schematic.Wire;

public class SchematicPopup implements Component {
	public interface SchematicInputBehavior {
		public void click(Schematic schematic, EnergyNode node, boolean left, boolean right);
	}
	private float lifetime = 0;
	private Schematic baseDiagram;
	private Rectangle bounds;
	private SchematicInputBehavior inputBehavior;
	private final float nodeScale = 10f;
	private static final Vector2 temp1 = new Vector2(), temp2 = new Vector2(), temp3 = new Vector2();
	private static final Color c1 = Color.RED;
	private static final Color c2 = Color.GREEN;
	private static final Color c3 = Color.BLUE;
	private static final float BORDER = 10;
	public SchematicPopup(Schematic diagram, SchematicInputBehavior inputBehavior, Rectangle bounds) {
		baseDiagram = diagram;
		this.inputBehavior = inputBehavior;
		this.bounds = new Rectangle(bounds.x + BORDER, bounds.y + BORDER, 
				bounds.width - BORDER * 2, bounds.height - BORDER * 2);
	}
	public void render(ShapeRenderer uiShapes, Batch uiBatch, float delta) {
		lifetime += delta;
		float flowPlace = (lifetime % 2);
		uiShapes.begin(ShapeType.Line);
		uiShapes.setColor(Color.GOLD);
		uiShapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		for (Wire wire : baseDiagram.getWires()) {
			temp1.set(baseDiagram.getLocation(wire.input));
			scaleToBounds(temp1);
			temp3.set(baseDiagram.getLocation(wire.output));
			scaleToBounds(temp3);
			if (flowPlace < 1)
				temp2.set(temp1).interpolate(temp3, flowPlace, Interpolation.pow2);
			else
				temp2.set(temp1).interpolate(temp3, 2 - flowPlace, Interpolation.pow2);
			uiShapes.line(temp1.x, temp1.y, temp2.x, temp2.y, c1, c2);
			uiShapes.line(temp2.x, temp2.y, temp3.x, temp3.y, c2, c3);
		}
		uiShapes.end();
		uiShapes.begin(ShapeType.Filled);
		for (EnergyNode node : baseDiagram.getNodes()) {
			temp3.set(baseDiagram.getLocation(node));
			scaleToBounds(temp3);
			uiShapes.circle(temp3.x, temp3.y, nodeScale);
		}
		uiShapes.end();
	}
	private void scaleToBounds(Vector2 temp) {
		temp.scl(bounds.width, bounds.height);
		temp.add(bounds.x, bounds.y);
	}
	public EnergyNode findNode(float mouseX, float mouseY) {
		System.out.println(String.format("%f, %f", mouseX, mouseY));
		for (EnergyNode node : baseDiagram.getNodes()) {
			temp3.set(baseDiagram.getLocation(node));
			scaleToBounds(temp3);
			if (temp3.dst(mouseX, mouseY) < nodeScale)
				return node;
		}
		return null;
	}
	public void handleClick(EnergyNode node, boolean left, boolean right) {
		inputBehavior.click(baseDiagram, node, left, right);
	}
}
