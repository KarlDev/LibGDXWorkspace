package indie.karl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;

public class MainController extends ApplicationAdapter {

	private AssetManager ass;
	private boolean loading = false;

	private PerspectiveCamera cam;
	private ModelBatch modelBatch;
	private Array<ModelInstance> instances = new Array<ModelInstance>();
	private Environment environment;
	private CameraInputController camController;
	
	@Override
	public void create () {
		ass = new AssetManager();

		modelBatch = new ModelBatch();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();



		//Light
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		ass.load("ball.g3db", Model.class);
		loading = true;
	}

	private void doneLoading() {
		Model ship = ass.get("ball.g3db", Model.class);
		ModelInstance shipInstance = new ModelInstance(ship);
		instances.add(shipInstance);
		loading = false;
	}

	@Override
	public void render () {
		if(loading && ass.update())
			doneLoading();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(cam);
		modelBatch.render(instances, environment);
		modelBatch.end();
	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
		instances.clear();
		ass.dispose();
	}
}
