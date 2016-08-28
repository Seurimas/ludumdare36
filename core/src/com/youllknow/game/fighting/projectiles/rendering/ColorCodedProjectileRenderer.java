package com.youllknow.game.fighting.projectiles.rendering;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ColorCodedProjectileRenderer implements ProjectileRendererComponent {
	static {
		ProjectileRenderer.addRenderer(ColorCodedProjectileRenderer.class);
	}
	private static TextureRegion shell, payload;
	public static void setTexture(Texture mainTexture) {
		shell = new TextureRegion(mainTexture, 0, 72, 6, 5);
		payload = new TextureRegion(mainTexture, 0, 77, 6, 5);
	}
	private final Color shellColor, payloadColor;
	public ColorCodedProjectileRenderer(Color shell, Color payload) {
		this.shellColor = shell;
		this.payloadColor = payload;
	}
	@Override
	public void draw(Batch batch, Entity entity, Vector2 position, float rotation, float deltaTime) {
		int width = shell.getRegionWidth() * 2;
		int height = shell.getRegionHeight() * 2;
		int offsetX = width / 2;
		int offsetY = height / 2;
		float x = position.x - offsetX;
		float y = position.y - offsetY;
		batch.setColor(shellColor);
		batch.draw(shell, x, y, 
				offsetX, offsetY, width, height, 1, 1, rotation);
		batch.setColor(payloadColor);
		batch.draw(payload, x, y, 
				offsetX, offsetY, width, height, 1, 1, rotation);
		batch.setColor(Color.WHITE);
	}

}
