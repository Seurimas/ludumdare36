package com.youllknow.game.fighting.rendering;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.youllknow.game.MainGameScreen;

public class BackdropRenderer extends EntitySystem {
	private final TextureRegion topBorder;
	private final TextureRegion bottomBorder;
	private final TextureRegion tile;
	private final TextureRegion borderTile;
	private final Batch batch;
	private final Viewport viewport;
	public BackdropRenderer(Batch batch, Viewport viewport, Texture texture) {
		this.batch = batch;
		this.viewport = viewport;
		topBorder = new TextureRegion(texture, 64, 32, 32, 16);
		bottomBorder = new TextureRegion(texture, 64, 48, 32, 16);
		tile = new TextureRegion(texture, 64, 64, 64, 32);
		borderTile = new TextureRegion(texture, 64, 96, 64, 32);
	}
	@Override
	public void update(float deltaTime) {
		batch.begin();
		int screenWidth = viewport.getScreenWidth() / 32;
		int screenHeight = (MainGameScreen.WORLD_HEIGHT) / 32;
		float centerX = viewport.getCamera().position.x;
		float offsetX = centerX % 64;
		float bottomY = 32;
		float topY = MainGameScreen.WORLD_HEIGHT - topBorder.getRegionHeight() - 32;
		for (int i = -screenWidth / 2 - 2;i <= screenWidth / 2 + 2;i++) {
			if (i % 2 == 0) {
				for (int j = 0;j < screenHeight;j++) {
					TextureRegion rowTile = tile;
					if (j == 0 || j == screenHeight - 1)
						rowTile = borderTile;
					batch.draw(rowTile, centerX + ((i + j % 2) * 32) - offsetX, j * 32);
				}
			}
		}
		for (int i = -screenWidth / 2 - 1;i <= screenWidth / 2 + 1;i++) {
			batch.draw(bottomBorder, centerX + (i * 32) - offsetX, bottomY);
			batch.draw(topBorder, centerX + (i * 32) - offsetX, topY);
		}
		batch.end();
	}
}
