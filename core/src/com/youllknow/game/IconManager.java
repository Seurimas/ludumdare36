package com.youllknow.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IconManager {
	private static Texture mainTexture;
	private static TextureRegion shieldIcon;
	public static Texture getMainTexture() {
		return mainTexture;
	}
	public static TextureRegion getShieldIcon() {
		return shieldIcon;
	}
	public static TextureRegion getHeatIcon() {
		return heatIcon;
	}
	public static TextureRegion getAttackIcon() {
		return attackIcon;
	}
	public static TextureRegion getChargeIcon() {
		return chargeIcon;
	}
	public static TextureRegion getHealthIcon() {
		return healthIcon;
	}
	public static TextureRegion getDamageTypeIcon() {
		return damageTypeIcon;
	}
	public static TextureRegion getDamageStrengthIcon() {
		return damageStrengthIcon;
	}
	public static TextureRegion getOffIcon() {
		return offIcon;
	}
	public static TextureRegion getLogicIcon() {
		return logicIcon;
	}
	public static TextureRegion getLogicIconInverted() {
		return logicIconInverted;
	}
	private static TextureRegion heatIcon;
	private static TextureRegion attackIcon;
	private static TextureRegion chargeIcon;
	private static TextureRegion healthIcon;
	private static TextureRegion damageTypeIcon;
	private static TextureRegion damageStrengthIcon;
	private static TextureRegion offIcon;
	private static TextureRegion logicIcon;
	private static TextureRegion logicIconInverted;
	public static void setTexture(Texture texture) {
		mainTexture = texture;
		shieldIcon = new TextureRegion(texture, 32, 64, 8, 8);
		heatIcon = new TextureRegion(texture, 32, 72, 8, 8);
		attackIcon = new TextureRegion(texture, 32, 80, 8, 8);
		chargeIcon = new TextureRegion(texture, 32, 88, 8, 8);
		healthIcon = new TextureRegion(texture, 40, 64, 8, 8);
		damageTypeIcon = new TextureRegion(texture, 40, 72, 8, 8);
		damageStrengthIcon = new TextureRegion(texture, 40, 80, 8, 8);
		offIcon = new TextureRegion(texture, 40, 88, 8, 8);
		logicIcon = new TextureRegion(texture, 32, 96, 16, 16);
		logicIconInverted = new TextureRegion(texture, 32, 112, 16, -16);
	}
	
}
