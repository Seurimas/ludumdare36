package com.youllknow.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.youllknow.game.fighting.DenizenUpdateSystem;
import com.youllknow.game.fighting.HealthComponent;
import com.youllknow.game.fighting.PlayerCameraSystem;
import com.youllknow.game.fighting.PlayerComponent;
import com.youllknow.game.fighting.WorldDenizen;
import com.youllknow.game.fighting.enemies.TankAiSystem;
import com.youllknow.game.fighting.enemies.TankEnemy;
import com.youllknow.game.fighting.input.PlayerWalkingSystem;
import com.youllknow.game.fighting.player.AttachedWeaponSystem;
import com.youllknow.game.fighting.player.PlayerShootingSystem;
import com.youllknow.game.fighting.player.AttachedWeapon;
import com.youllknow.game.fighting.projectiles.ProjectileCollisionSystem;
import com.youllknow.game.fighting.projectiles.ProjectileMovementSystem;
import com.youllknow.game.fighting.projectiles.ProjectileWeapon;
import com.youllknow.game.fighting.projectiles.behaviors.SingleShotBehavior;
import com.youllknow.game.fighting.rendering.DebugWorldRenderer;
import com.youllknow.game.fighting.world.FlooredGravitySystem;
import com.youllknow.game.wiring.Schematic;
import com.youllknow.game.wiring.Schematic.EnergyNode;
import com.youllknow.game.wiring.SchematicInputSystem;
import com.youllknow.game.wiring.SchematicPopup;
import com.youllknow.game.wiring.SchematicPopup.SchematicInputBehavior;
import com.youllknow.game.wiring.SchematicRenderer;
import com.youllknow.game.wiring.nodes.XorEnergyNode;

public class MainGameScreen implements Screen {
	private final LudumDare36Game game;
	private static final int SCREEN_HEIGHT = 600; 
	public static final int LOWER_UI_HEIGHT = 150;
	public static final int SCREEN_WIDTH = 800;
	private OrthographicCamera camera = new OrthographicCamera(800, SCREEN_HEIGHT);
	private Viewport viewport = new StretchViewport(800, SCREEN_HEIGHT, camera);
	private final Engine engine;
	public MainGameScreen(LudumDare36Game game) {
		this.game = game;
		viewport.apply(true);
		this.engine = new Engine();
		setupEngine();
	}
	private void setupEngine() {
		engine.addSystem(new PlayerCameraSystem(camera));
		engine.addSystem(new SchematicRenderer(game.uiShapes, game.uiBatch));
		engine.addSystem(new DebugWorldRenderer(game.batch, game.shapes));
		engine.addSystem(new SchematicInputSystem(game.input));
		engine.addSystem(new PlayerShootingSystem(game.input));
		engine.addSystem(new AttachedWeaponSystem());
		engine.addSystem(new ProjectileMovementSystem());
		engine.addSystem(new PlayerWalkingSystem());
		engine.addSystem(new DenizenUpdateSystem());
		engine.addSystem(new FlooredGravitySystem());
		engine.addSystem(new ProjectileCollisionSystem());
		engine.addSystem(new TankAiSystem());
		Schematic diagram = createDebugSchematic();
		Entity popupEnt = createSchematicPopup(diagram);
		engine.addEntity(popupEnt);
		Entity player = createPlayer();
		engine.addEntity(player);
//		engine.addEntity(createTargetDummy());
		TankEnemy.spawn(engine, player, new Vector2(500, 200));
	}
	private Entity createPlayer() {
		Entity entity = new Entity();
		PlayerComponent player = new PlayerComponent();
		WorldDenizen denizen = new WorldDenizen(new Rectangle(0, 0, 50, 50), 10);
		AttachedWeapon weapon = new AttachedWeapon(entity, 25, 25);
		ProjectileWeapon projectileWeapon = new ProjectileWeapon(entity, new SingleShotBehavior(5));
		entity.add(player);
		entity.add(denizen);
		entity.add(weapon);
		entity.add(projectileWeapon);
		entity.add(new HealthComponent(100));
		return entity;
	}
	private Entity createTargetDummy() {
		Entity entity = new Entity();
		WorldDenizen denizen = new WorldDenizen(new Rectangle(500, 200, 50, 50), 10);
		entity.add(denizen);
		entity.add(new HealthComponent(50));
		return entity;
	}
	private Entity createSchematicPopup(Schematic diagram) {
		Entity popupEnt = new Entity();
		popupEnt.add(new SchematicPopup(diagram, new SchematicInputBehavior() {
			@Override
			public void click(Schematic schematic, EnergyNode node, boolean left, boolean right) {
				System.out.println(node.getId());
			}
		}, new Rectangle(0, 0, 200, LOWER_UI_HEIGHT)));
		return popupEnt;
	}
	private Schematic createDebugSchematic() {
		Schematic diagram = new Schematic();
		EnergyNode node1 = new XorEnergyNode();
		diagram.addNode(node1);
		diagram.setLocation(node1, 0, 0.66f);
		EnergyNode node2 = new XorEnergyNode();
		diagram.addNode(node2);
		diagram.setLocation(node2, 0, 0.33f);
		EnergyNode node3 = new XorEnergyNode();
		diagram.addNode(node3);
		diagram.setLocation(node3, 0.25f, 0.5f);
		diagram.addWire(node1, node3);
		diagram.addWire(node2, node3);
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
	}

	@Override
	public void dispose() {
	}

}
