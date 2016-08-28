package com.youllknow.game.fighting.enemies;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.youllknow.game.fighting.rendering.DenizenRenderer;
import com.youllknow.game.fighting.rendering.DenizenRendererComponent;

public class SimpleSpriteRenderer implements DenizenRendererComponent {
	static {
		DenizenRenderer.addRenderer(SimpleSpriteRenderer.class);
	}
	private TextureRegion sprite;
	public SimpleSpriteRenderer(TextureRegion sprite) {
		this.sprite = sprite;
	}
	@Override
	public void draw(Batch batch, Entity entity, Rectangle area, float deltaTime) {
		batch.draw(sprite, area.x, area.y, area.width, area.height);
	}

}
