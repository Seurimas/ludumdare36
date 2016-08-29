package com.youllknow.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.youllknow.game.ancient.GameStateEnergyInputNode;
import com.youllknow.game.ancient.GameStateEnergyOutputNode;
import com.youllknow.game.ancient.GameStateEnergyOutputNode.GameStateGetter;
import com.youllknow.game.ancient.IntroSystem;
import com.youllknow.game.fighting.DenizenUpdateSystem;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.fighting.HealthComponent.DamageType;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.enemies.DeathSystem;
import com.youllknow.game.fighting.enemies.ExplosionDeathBehavior;
import com.youllknow.game.fighting.enemies.HelicopterAiSystem;
import com.youllknow.game.fighting.enemies.HelicopterEnemy;
import com.youllknow.game.fighting.enemies.SiloAiSystem;
import com.youllknow.game.fighting.enemies.SiloEnemy;
import com.youllknow.game.fighting.enemies.TankAiSystem;
import com.youllknow.game.fighting.enemies.TankEnemy;
import com.youllknow.game.fighting.levels.EnemySpawningSystem;
import com.youllknow.game.fighting.player.AttachedWeaponSystem;
import com.youllknow.game.fighting.player.HealthHeatShieldRenderer;
import com.youllknow.game.fighting.player.PlayerAdvancementSystem;
import com.youllknow.game.fighting.player.PlayerCameraSystem;
import com.youllknow.game.fighting.player.PlayerComponent;
import com.youllknow.game.fighting.player.PlayerDeathBehavior;
import com.youllknow.game.fighting.player.PlayerFlyingSystem;
import com.youllknow.game.fighting.player.PlayerShootingSystem;
import com.youllknow.game.fighting.player.PlayerWalkingSystem;
import com.youllknow.game.fighting.player.PlayerWeapon;
import com.youllknow.game.fighting.player.ShieldDamageListener;
import com.youllknow.game.fighting.player.ShieldSystem;
import com.youllknow.game.fighting.player.nodes.DamageStrengthGetter;
import com.youllknow.game.fighting.player.nodes.DamageTypeGetter;
import com.youllknow.game.fighting.player.nodes.HealthGetter;
import com.youllknow.game.fighting.player.nodes.HeatGetter;
import com.youllknow.game.fighting.player.nodes.MatchDamageStrengthSetter;
import com.youllknow.game.fighting.player.nodes.MatchDamageTypeSetter;
import com.youllknow.game.fighting.player.nodes.MatchHeatSetter;
import com.youllknow.game.fighting.player.nodes.PlayerWeaponSetter;
import com.youllknow.game.fighting.player.nodes.WeaponChargeGetter;
import com.youllknow.game.fighting.player.nodes.WeaponSettingsGetter;
import com.youllknow.game.fighting.player.nodes.PlayerWeaponSetter.WeaponStat;
import com.youllknow.game.fighting.player.AttachedWeapon;
import com.youllknow.game.fighting.projectiles.NonOwnerTargetBehavior;
import com.youllknow.game.fighting.projectiles.ProjectileCollisionSystem;
import com.youllknow.game.fighting.projectiles.ProjectileMovementSystem;
import com.youllknow.game.fighting.projectiles.ProjectileWeapon;
import com.youllknow.game.fighting.projectiles.behaviors.SingleShotBehavior;
import com.youllknow.game.fighting.projectiles.rendering.ColorCodedProjectileRenderer;
import com.youllknow.game.fighting.projectiles.rendering.ProjectileRenderer;
import com.youllknow.game.fighting.rendering.BackdropRenderer;
import com.youllknow.game.fighting.rendering.DebugWorldRenderer;
import com.youllknow.game.fighting.rendering.DenizenRenderer;
import com.youllknow.game.fighting.rendering.DenizenRendererComponent;
import com.youllknow.game.fighting.world.FlooredGravitySystem;
import com.youllknow.game.utils.TooltipManager;
import com.youllknow.game.wiring.PowerFlow;
import com.youllknow.game.wiring.Schematic;
import com.youllknow.game.wiring.Schematic.EnergyNode;
import com.youllknow.game.wiring.Schematic.EnergyNode.Energy;
import com.youllknow.game.wiring.SchematicInputSystem;
import com.youllknow.game.wiring.SchematicPopup;
import com.youllknow.game.wiring.SchematicPopup.SchematicInputBehavior;
import com.youllknow.game.wiring.SchematicRenderer;
import com.youllknow.game.wiring.TooltipSystem;
import com.youllknow.game.wiring.nodes.LogicEnergyNode;

public class MainGameScreen implements Screen {
	private final LudumDare36Game game;
	public static final int SCREEN_HEIGHT = 600; 
	public static final int LOWER_UI_HEIGHT = 150; 
	public static final int WORLD_HEIGHT = SCREEN_HEIGHT - LOWER_UI_HEIGHT;
	public static final int SCREEN_WIDTH = 800;
	private OrthographicCamera camera = new OrthographicCamera(800, SCREEN_HEIGHT);
	private Viewport viewport = new StretchViewport(800, SCREEN_HEIGHT, camera);
	private final Engine engine;
	Music entryTheme;
	Music mainTheme;
	public MainGameScreen(LudumDare36Game game) {
		this.game = game;
		viewport.apply(true);
		this.engine = new Engine();
		Texture mainTexture = game.assets.get(game.MAIN_TEXTURE, Texture.class);
		entryTheme = game.assets.get(LudumDare36Game.ENTRANCE_TUNE, Music.class);
		mainTheme = game.assets.get(LudumDare36Game.MAIN_THEME, Music.class);
		IconManager.setTexture(mainTexture);
		ColorCodedProjectileRenderer.setTexture(mainTexture);
		setupEngine();
		TankEnemy.setSprite(new TextureRegion(mainTexture, 0, 32, 32, 32));
		HelicopterEnemy.setSprite(new TextureRegion(mainTexture, 32, 32, 32, 32));
		SiloEnemy.setSprite(new TextureRegion(mainTexture, 0, 96, 32, 32));
		TankEnemy.setFireSound(game.assets.get(game.THEIR_GUN, Sound.class));
		HelicopterEnemy.setFireSound(game.assets.get(game.THEIR_GUN, Sound.class));
		SiloEnemy.setFireSound(game.assets.get(game.SILO_GUN, Sound.class));
	}
	private void setupEngine() {
		TooltipManager tooltips = new TooltipManager();
		Entity player = createPlayer();
		engine.addEntity(player);
		Texture mainTexture = game.assets.get(game.MAIN_TEXTURE, Texture.class);
		engine.addSystem(new PlayerCameraSystem(camera));
		engine.addSystem(new BackdropRenderer(game.batch, viewport, mainTexture));
		engine.addSystem(new SchematicRenderer(game.uiShapes, game.uiBatch));
		Rectangle healthArea = new Rectangle(0, LOWER_UI_HEIGHT - 25, SCREEN_WIDTH / 2, 25);
		Rectangle heatArea = new Rectangle(SCREEN_WIDTH / 2, LOWER_UI_HEIGHT - 25, SCREEN_WIDTH / 2, 25);
		engine.addSystem(new HealthHeatShieldRenderer(player, game.uiShapes, game.uiBatch,
				healthArea, heatArea));
		Rectangle tooltipArea = new Rectangle(64, SCREEN_HEIGHT - 96, SCREEN_WIDTH - 128, 64);
		engine.addSystem(new DebugWorldRenderer(game.batch, game.shapes));
		engine.addSystem(new DenizenRenderer(game.batch));
		engine.addSystem(new ProjectileRenderer(game.batch));
		engine.addSystem(new SchematicInputSystem(game.input, tooltips));
		engine.addSystem(new IntroSystem(player, entryTheme, mainTheme, game.uiShapes, tooltips));
		engine.addSystem(new TooltipSystem(game.uiBatch, game.uiShapes, new BitmapFont(), tooltipArea, tooltips));
		engine.addSystem(new PlayerShootingSystem(game.input));
		engine.addSystem(new AttachedWeaponSystem());
		engine.addSystem(new ProjectileMovementSystem());
		engine.addSystem(new PlayerFlyingSystem());
		engine.addSystem(new DenizenUpdateSystem());
//		engine.addSystem(new FlooredGravitySystem());
		engine.addSystem(new PlayerAdvancementSystem());
		engine.addSystem(new ProjectileCollisionSystem());
		engine.addSystem(new TankAiSystem());
		engine.addSystem(new HelicopterAiSystem());
		engine.addSystem(new SiloAiSystem());
		engine.addSystem(new DeathSystem());
		engine.addSystem(new ShieldSystem());
		setupWiring(player);
		engine.addSystem(new EnemySpawningSystem(player));
	}
	private void setupWiring(Entity player) {
		Schematic sheildSchematic = createShieldSchematic(player);
		Entity popupEnt1 = createSchematicPopup(sheildSchematic, new Rectangle(0, 0, SCREEN_WIDTH / 2, LOWER_UI_HEIGHT - 25));
		PowerFlow shieldPowerFlow = initializePowerFlow(sheildSchematic, popupEnt1.getComponent(SchematicPopup.class));
		engine.addEntity(popupEnt1);
		Schematic weaponSchematic = createWeaponSchematic(player);
		Entity popupEnt2 = createSchematicPopup(weaponSchematic, new Rectangle(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, LOWER_UI_HEIGHT - 25));
		PowerFlow weaponPowerFlow = initializePowerFlow(weaponSchematic, popupEnt2.getComponent(SchematicPopup.class));
		engine.addEntity(popupEnt2);
//		Schematic thirdSchematic = createHeatSinkSchematic(player);
//		Entity popupEnt3 = createSchematicPopup(thirdSchematic, new Rectangle(SCREEN_WIDTH / 2, LOWER_UI_HEIGHT / 2, SCREEN_WIDTH / 2, LOWER_UI_HEIGHT / 2));
//		engine.addEntity(popupEnt3);
		player.getComponent(PlayerWeapon.class).setPowerFlow(weaponPowerFlow);
		player.getComponent(HealthComponent.class).setListener(new ShieldDamageListener(shieldPowerFlow));
	}
	private Entity createPlayer() {
		Entity entity = new Entity();
		Texture mainTexture = game.assets.get(game.MAIN_TEXTURE, Texture.class);
		Sound playerFireSound = game.assets.get(game.MY_GUN, Sound.class);
		Sound shutOffSound = game.assets.get(game.SHUT_OFF, Sound.class);
		Sound shieldUpSound = game.assets.get(game.SHIELD_UP, Sound.class);
		PlayerComponent player = new PlayerComponent(mainTexture);
		WorldDenizen denizen = new WorldDenizen(new Rectangle(0, 0, 50, 50), 10);
//		AttachedWeapon weapon = new AttachedWeapon(entity, 25, 25);
//		ProjectileWeapon projectileWeapon = new ProjectileWeapon(entity, new SingleShotBehavior(DamageType.ENERGY, 5), new NonOwnerTargetBehavior());
		entity.add(player);
		entity.add(denizen);
//		entity.add(weapon);
//		entity.add(projectileWeapon);
		entity.add(new HealthComponent(100, new PlayerDeathBehavior(game)));
		entity.add(new PlayerWeapon(new TextureRegion(mainTexture, 0, 64, 9, 9), playerFireSound, shutOffSound, shieldUpSound));
		return entity;
	}
	private Entity createSchematicPopup(Schematic diagram, Rectangle area) {
		Entity popupEnt = new Entity();
		SchematicPopup popup = new SchematicPopup(diagram, new SchematicInputBehavior() {
			@Override
			public void click(Schematic schematic, EnergyNode node, boolean left, boolean right) {
				if (left) {
					if (node instanceof LogicEnergyNode) {
						((LogicEnergyNode)node).toggleLogic();
					}
				}
			}
		}, new BitmapFont(), area);
		popupEnt.add(popup);
		
		return popupEnt;
	}
	private PowerFlow initializePowerFlow(Schematic diagram, SchematicPopup popup) {
		PowerFlow powerFlow = new PowerFlow(diagram);
		powerFlow.calculate();
		popup.setPowerFlow(powerFlow);
		return powerFlow;
	}
	private Schematic createWeaponSchematic(Entity player) {
		Schematic diagram = new Schematic("Phasers");
		EnergyNode heatLevel = new GameStateEnergyOutputNode(Color.RED, IconManager.getHeatIcon(), new HeatGetter(player));
		EnergyNode weaponSettings = new GameStateEnergyOutputNode(Color.YELLOW, IconManager.getAttackIcon(), new WeaponSettingsGetter(player));
		EnergyNode weaponCharge = new GameStateEnergyOutputNode(Color.GOLD, IconManager.getChargeIcon(), new WeaponChargeGetter(player));
		diagram.addNode(heatLevel);
		diagram.addNode(weaponSettings);
		diagram.addNode(weaponCharge);
		diagram.setLocation(weaponSettings, 0, 2 / 2f);
		diagram.setLocation(weaponCharge, 0, 1 / 2f);
		diagram.setLocation(heatLevel, 0, 0 / 2f);
		EnergyNode subnode11 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		EnergyNode subnode12 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		diagram.addNode(subnode11);
		diagram.addNode(subnode12);
		diagram.addWire(weaponSettings, subnode11);
		diagram.addWire(weaponCharge, subnode11);
		diagram.addWire(weaponCharge, subnode12);
		diagram.addWire(heatLevel, subnode12);
		diagram.setLocation(subnode11, 0.25f, 3 / 4f);
		diagram.setLocation(subnode12, 0.25f, 1 / 4f);
		EnergyNode subnode21 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		EnergyNode shutOff = new GameStateEnergyInputNode(Color.CYAN, IconManager.getOffIcon(), new PlayerWeaponSetter(WeaponStat.SHUT_OFF, player));
		EnergyNode healthLevel = new GameStateEnergyOutputNode(Color.GREEN, IconManager.getHealthIcon(), new HealthGetter(player));
		diagram.addNode(subnode21);
		diagram.addNode(shutOff);
		diagram.addNode(healthLevel);
		diagram.addWire(subnode11, subnode21);
		diagram.addWire(subnode12, subnode21);
		diagram.addWire(subnode12, shutOff);
		diagram.setLocation(subnode21, 0.5f, 1 / 2f);
		diagram.setLocation(shutOff, 0.4375f, 0 / 2f);
		diagram.setLocation(healthLevel, 0.5625f, 0 / 2f);
		EnergyNode subnode31 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		EnergyNode fireOrCharge = new GameStateEnergyInputNode(Color.GOLD, IconManager.getChargeIcon(), new PlayerWeaponSetter(WeaponStat.FIRE_CHARGE, player));
		diagram.addNode(subnode31);
		diagram.addNode(fireOrCharge);
		diagram.addWire(subnode21, subnode31);
		diagram.addWire(healthLevel, subnode31);
		diagram.addWire(subnode21, fireOrCharge);
		diagram.setLocation(subnode31, 0.75f, 1 / 4f);
		diagram.setLocation(fireOrCharge, 0.75f, 3 / 4f);
		EnergyNode drainShields = new GameStateEnergyInputNode(Color.BLUE, IconManager.getShieldIcon(), new PlayerWeaponSetter(WeaponStat.DRAIN_SHIELD, player));
		diagram.addNode(drainShields);
		diagram.addWire(subnode31, drainShields);
		diagram.setLocation(drainShields, 1, 1 / 2f);
		return diagram;
	}
	private Schematic createShieldSchematic(final Entity player) {
		Schematic diagram = new Schematic("Shields");
		EnergyNode dmgStrength = new GameStateEnergyOutputNode(Color.FIREBRICK, IconManager.getDamageStrengthIcon(), new DamageStrengthGetter(player));
		EnergyNode dmgType = new GameStateEnergyOutputNode(Color.GOLDENROD, IconManager.getDamageTypeIcon(), new DamageTypeGetter(player));
		EnergyNode heatLevel = new GameStateEnergyOutputNode(Color.RED, IconManager.getHeatIcon(), new HeatGetter(player));
		EnergyNode healthLevel = new GameStateEnergyOutputNode(Color.GREEN, IconManager.getHealthIcon(), new HealthGetter(player));
		diagram.addNode(dmgStrength);
		diagram.addNode(dmgType);
		diagram.addNode(heatLevel);
		diagram.addNode(healthLevel);
		diagram.setLocation(dmgType, 0, 2f / 3);
		diagram.setLocation(dmgStrength, 0, 1f / 3);
		diagram.setLocation(heatLevel, 0, 3f / 3);
		diagram.setLocation(healthLevel, 0, 0f / 3);
		EnergyNode subnode11 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		EnergyNode subnode12 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		EnergyNode subnode13 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		diagram.addNode(subnode11);
		diagram.addNode(subnode12);
		diagram.addNode(subnode13);
		diagram.addWire(heatLevel, subnode11);
		diagram.addWire(dmgType, subnode11);
		diagram.addWire(dmgType, subnode12);
		diagram.addWire(dmgStrength, subnode12);
		diagram.addWire(dmgStrength, subnode13);
		diagram.addWire(healthLevel, subnode13);
		diagram.setLocation(subnode11, 0.25f, 5f / 6);
		diagram.setLocation(subnode12, 0.25f, 3f / 6);
		diagram.setLocation(subnode13, 0.25f, 1f / 6);
		EnergyNode subnode21 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		EnergyNode subnode22 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		diagram.addNode(subnode21);
		diagram.addNode(subnode22);
		diagram.addWire(subnode11, subnode21);
		diagram.addWire(subnode12, subnode21);
		diagram.addWire(subnode12, subnode22);
		diagram.addWire(subnode13, subnode22);
		diagram.setLocation(subnode21, 0.5f, 4f / 6);
		diagram.setLocation(subnode22, 0.5f, 2f / 6);
		EnergyNode subnode31 = new LogicEnergyNode(LogicEnergyNode.sameOrNeither);
		EnergyNode matchDamageType = new GameStateEnergyInputNode(Color.GOLDENROD, IconManager.getDamageTypeIcon(), new MatchDamageTypeSetter(player));
		EnergyNode matchDamageStrength = new GameStateEnergyInputNode(Color.FIREBRICK, IconManager.getDamageStrengthIcon(), new MatchDamageStrengthSetter(player));
		diagram.addNode(subnode31);
		diagram.addNode(matchDamageType);
		diagram.addNode(matchDamageStrength);
		diagram.addWire(subnode21, matchDamageType);
		diagram.addWire(subnode22, matchDamageStrength);
		diagram.addWire(subnode21, subnode31);
		diagram.addWire(subnode22, subnode31);
		diagram.setLocation(matchDamageType, 0.75f, 5f / 6);
		diagram.setLocation(subnode31, 0.75f, 3f / 6);
		diagram.setLocation(matchDamageStrength, 0.75f, 1f / 6);
		EnergyNode matchHeatLevel = new GameStateEnergyInputNode(Color.RED, IconManager.getHeatIcon(), new MatchHeatSetter(player));
		diagram.addNode(matchHeatLevel);
		diagram.addWire(subnode31, matchHeatLevel);
		diagram.setLocation(matchHeatLevel, 1f, 1f / 2);
		return diagram;
	}
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.setScreenPosition(0, 0);
		game.batch.setProjectionMatrix(camera.combined);
		game.shapes.setProjectionMatrix(camera.combined);
		game.input.update(viewport, delta);
		engine.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
//		uiCamera.position.add(0, 0, 0);
//		uiCamera.update();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		mainTheme.stop();
		mainTheme.dispose();
		entryTheme.stop();
		entryTheme.dispose();
	}

	@Override
	public void dispose() {
		mainTheme.stop();
		mainTheme.dispose();
		entryTheme.stop();
		entryTheme.dispose();
	}

}
