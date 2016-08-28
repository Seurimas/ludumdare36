package com.youllknow.game.wiring;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.youllknow.game.utils.TooltipManager;

public class TooltipSystem extends EntitySystem {
	private final Batch uiBatch;
	private final ShapeRenderer uiShapes;
	private final BitmapFont font;
	private final Rectangle area;
	private final TooltipManager tooltips;
	public TooltipSystem(Batch uiBatch, ShapeRenderer uiShapes, BitmapFont font,
			Rectangle area, TooltipManager tooltips) {
		this.uiBatch = uiBatch;
		this.uiShapes = uiShapes;
		this.font = font;
		this.area = area;
		this.tooltips = tooltips;
	}
	@Override
	public void update(float deltaTime) {
		if (tooltips.tooltip != null) {
			uiShapes.begin(ShapeType.Filled);
			uiShapes.setColor(Color.BLACK);
			uiShapes.rect(area.x, area.y, area.width, area.height);
			uiShapes.end();
			uiBatch.begin();
			GlyphLayout layout = new GlyphLayout(font, tooltips.tooltip, 0, tooltips.tooltip.length(), 
					Color.GOLD, area.width, Align.center, true, null);
			font.draw(uiBatch, layout, area.x, area.y + layout.height / 2 + area.height / 2);
			uiBatch.end();
		}
	}
}
