package com.youllknow.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.youllknow.game.MainGameScreen;

public class InputMarshal { 
	public void update(Viewport viewport, float delta) {
		updateMouse(viewport, Gdx.input.getX(), Gdx.input.getY(),
				Gdx.input.isButtonPressed(Buttons.LEFT), Gdx.input.isButtonPressed(Buttons.RIGHT), delta);
	}
	private float clickDurations[] = new float[Math.max(Buttons.RIGHT, Buttons.LEFT) + 1];
	private final Vector2 temp = new Vector2();
	public float uiX, uiY;
	public float worldX, worldY;
	private void updateMouse(Viewport viewport, float x, float y, 
			boolean leftPressed, boolean rightPressed, float delta) {
		temp.set(x, y);
		uiX = temp.x;uiY = viewport.getScreenHeight() - temp.y;
		viewport.unproject(temp);
		worldX = temp.x;worldY = temp.y;
		updateButton(Buttons.LEFT, delta, leftPressed);
		updateButton(Buttons.RIGHT, delta, rightPressed);
	}
	private void updateButton(int button, float delta, boolean pressed) {
		if (pressed) {
			if (clickDurations[button] >= button)
				clickDurations[button] += delta;
			else
				clickDurations[button] = button;
		} else {
			clickDurations[button] = -1;
		}
	}
	public boolean isLeftJustClicked(boolean ui) {
		if (clickDurations[Buttons.LEFT] == 0)
			return mouseInUI() == ui;
		else
			return false;
	}
	public boolean isRightJustClicked(boolean ui) {
		if (clickDurations[Buttons.RIGHT] == 0)
			return mouseInUI() == ui;
		else
			return false;
	}
	public boolean mouseInUI() {
		return uiY < MainGameScreen.LOWER_UI_HEIGHT;
	}
}
