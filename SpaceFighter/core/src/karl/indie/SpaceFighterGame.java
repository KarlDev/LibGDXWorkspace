package karl.indie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import karl.indie.game.GameStage;
import karl.indie.menu.main.MainMenuStage;

public class SpaceFighterGame extends ApplicationAdapter {



	public static int MODE_PLAY = 0;
	public static int MODE_MAIN_MENU = 1;
	public static int MODE_OPTIONS_MENU = 2;

	private OrthographicCamera camera;
	private FitViewport viewport;
	private SpriteBatch spriteBatch;
	private ShapeRenderer sr;
	Polygon p = new Polygon(new float[]{0, 0, 200, 0, 200, 200, 0, 200});
	Vector2 pOrigin = new Vector2(500, 500);

	private int currentMode;
	GameStage gameStage;
	MainMenuStage menuStage;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1248, 780);
		viewport = new FitViewport(1248, 780, camera);
		spriteBatch = new SpriteBatch();
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(spriteBatch.getProjectionMatrix());
		//sr.translate(pOrigin.x, pOrigin.y, 0);
		p.setPosition(300, 300);
		p.setOrigin(100, 100);
		System.out.println(p);

		setMode(MODE_PLAY);
		//setMode(MODE_MAIN_MENU);
		//camera.zoom -= 0.5;

	}

	public void setMode(int mode) {
		switch (mode) {
			case 0:
				gameStage = new GameStage(this);
				gameStage.setViewport(viewport);
				Gdx.input.setInputProcessor(gameStage);
				break;
			case 1:
				menuStage = new MainMenuStage(this);
				menuStage.setViewport(viewport);
				Gdx.input.setInputProcessor(menuStage);
				break;
		}
		currentMode = mode;
	}

	float test = 0f;
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		test += Gdx.graphics.getDeltaTime();
		if(test >= 1) {
			p.rotate(45);
			for (float f : p.getTransformedVertices())
				System.out.print("|" + f);
			System.out.println("|Rotation: " + p.getRotation() + "|");
			test = 0;
		}
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.polygon(p.getTransformedVertices());
		sr.end();
		spriteBatch.begin();
		switch (currentMode) {
			case 0:
				gameStage.act(Gdx.graphics.getDeltaTime());
				gameStage.draw();
				break;
			case 1:
				menuStage.act(Gdx.graphics.getDeltaTime());
				menuStage.draw();
				break;
		}
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		if(gameStage != null) gameStage.dispose();
		if(menuStage != null) menuStage.dispose();
		spriteBatch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height, true);
	}
}
