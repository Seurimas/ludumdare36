package com.youllknow.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputMarshal { 
	public void update(Viewport viewport, float delta) {
		updateMouse(viewport, Gdx.input.getX(), Gdx.input.getY(),
				Gdx.input.isButtonPressed(Buttons.LEFT), Gdx.input.isButtonPressed(Buttons.RIGHT), delta);
	}
	private float clickDurations[] = new float[Math.max(Buttons.RIGHT, Buttons.LEFT) + 1];
	private final Vector2 temp = new Vector2();
	public float mouseX, mouseY;
	private void updateMouse(Viewport viewport, float x, float y, 
			boolean leftPressed, boolean rightPressed, float delta) {
		temp.set(x, y);
		viewport.unproject(temp);
		mouseX = temp.x;mouseY = temp.y;
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
	public boolean isLeftJustClicked() {
		return clickDurations[Buttons.LEFT] == 0;
	}
	public boolean isRightJustClicked() {
		return clickDurations[Buttons.RIGHT] == 0;
	}
}
