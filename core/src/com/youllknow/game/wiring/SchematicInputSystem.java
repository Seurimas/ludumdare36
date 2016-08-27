package com.youllknow.game.wiring;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.youllknow.game.input.InputMarshal;
import com.youllknow.game.wiring.Schematic.EnergyNode;

public class SchematicInputSystem extends EntitySystem {
	private final Family entityFamily = Family.all(SchematicPopup.class).get();
	private final InputMarshal input;
	public SchematicInputSystem(InputMarshal input) {
		this.input = input;
	}
	@Override
	public void update(float deltaTime) {
		if (input.isLeftJustClicked() || input.isRightJustClicked()) {
			for (Entity entity : getEngine().getEntitiesFor(entityFamily)) {
				SchematicPopup popup = entity.getComponent(SchematicPopup.class);
				EnergyNode node = popup.findNode(input.mouseX, input.mouseY);
				if (node != null) {
					popup.handleClick(node, input.isLeftJustClicked(), input.isRightJustClicked());
					return;
				}
			}
		}
	}
}
