package karl.indie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.viewport.FitViewport;
import karl.indie.game.GameScreen;

public class BallKickerApp extends Game {

	public OrthographicCamera camera;
	public FitViewport viewport;
	public SpriteBatch spriteBatch;

	public AssetManager assManager;
	
	@Override
	public void create () {
		//Camera etc
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT);
		viewport = new FitViewport(GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT, camera);
		spriteBatch = new SpriteBatch();

		//Asset Manager
		assManager = new AssetManager();

		//Box2D
		Box2D.init();

		//Game screen
		GameScreen gs = new GameScreen();
		gs.setBKA(this);
		setScreen(gs);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
		camera.update();
	}
}
