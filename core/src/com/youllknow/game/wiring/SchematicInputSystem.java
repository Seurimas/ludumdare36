package com.youllknow.game.wiring;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.youllknow.game.input.InputMarshal;
import com.youllknow.game.utils.TooltipManager;
import com.youllknow.game.wiring.Schematic.EnergyNode;

public class SchematicInputSystem extends EntitySystem {
	private final Family entityFamily = Family.all(SchematicPopup.class).get();
	private final InputMarshal input;
	private final TooltipManager tooltip;
	public SchematicInputSystem(InputMarshal input, TooltipManager tooltip) {
		this.input = input;
		this.tooltip = tooltip;
	}
	@Override
	public void update(float deltaTime) {
		if (input.isLeftJustClicked(true) || input.isRightJustClicked(true)) {
			for (Entity entity : getEngine().getEntitiesFor(entityFamily)) {
				SchematicPopup popup = entity.getComponent(SchematicPopup.class);
				EnergyNode node = popup.findNode(input.uiX, input.uiY);
				if (node != null) {
					popup.handleClick(node, input.isLeftJustClicked(true), input.isRightJustClicked(true));
					return;
				}
			}
		}
		EnergyNode foundNode = null;
		for (Entity entity : getEngine().getEntitiesFor(entityFamily)) {
			SchematicPopup popup = entity.getComponent(SchematicPopup.class);
			EnergyNode node = popup.findNode(input.uiX, input.uiY);
			if (node != null)
				foundNode = node;
		}
		tooltip.setForNode(foundNode);
	}
}
