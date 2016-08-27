package com.youllknow.game.fighting.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.youllknow.game.fighting.rendering.DenizenRenderer;
import com.youllknow.game.fighting.rendering.DenizenRendererComponent;

public class PlayerComponent implements Component, DenizenRendererComponent {
	static {
		DenizenRenderer.addRenderer(PlayerComponent.class);
	}
	public boolean walking = false;
	private float duration = 0;
	private final Texture sprite;
	public PlayerComponent(Texture texture4Frame) {
		sprite = texture4Frame;
	}
	public void draw(Batch batch, Rectangle area, float deltaTime) {
		int frame = 0;
		if (walking) {
			duration += deltaTime;
			frame = (int)(duration * 8) % 4;
		} else {
			duration = 0;
		}
		batch.draw(sprite, area.x, area.y, area.width, area.height, 
				 frame / 4f, 0.5f, frame / 4f + 0.25f, 0);
	}
}
