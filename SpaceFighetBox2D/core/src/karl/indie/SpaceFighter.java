package karl.indie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SpaceFighter extends ApplicationAdapter {
	private OrthographicCamera camera;
	private FitViewport viewport;
	private SpriteBatch spriteBatch;

	private World world;
	private Body player;
	private Body circ;
	private Box2DDebugRenderer debugRen;
	
	@Override
	public void create () {
		//Camer etc
		camera = new OrthographicCamera();
		//camera.setToOrtho(false, 1248, 780);
		viewport = new FitViewport(1248, 780, camera);
		spriteBatch = new SpriteBatch();

		//Box2D
		Box2D.init();
		debugRen = new Box2DDebugRenderer();
		world = new World(new Vector2(0, 0), true);

		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(200, 200);
		def.fixedRotation = true;
		player = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50, 50);
		player.createFixture(shape, 1f);
		shape.dispose();

		BodyDef def1 = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		def.fixedRotation = true;
		circ = world.createBody(def1);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(100f);
		circleShape.setPosition(new Vector2(624, 390));
		circ.createFixture(circleShape, 10000.0f);
		circleShape.dispose();

	}

	@Override
	public void render () {
		updateWorld(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRen.render(world, camera.combined);
	}

	private void updateWorld(float delta) {
		world.step(delta, 6, 2);
		System.out.println(player.getWorldCenter());
	}
	
	@Override
	public void dispose () {
		world.dispose();
		debugRen.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height, true);
	}
}
