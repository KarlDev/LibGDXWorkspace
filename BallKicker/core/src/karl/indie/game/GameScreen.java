package karl.indie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import karl.indie.BallKickerApp;
import karl.indie.game.control.GameWorld;
import karl.indie.game.view.BallView;

/**
 * Created by Karl on 05.08.2017.
 */
public class GameScreen implements Screen {
    public static final float WORLD_WIDTH = 1080, WORLD_HEIGHT = 1920;

    public BallKickerApp bka;


    private Box2DDebugRenderer debugRen;

    private GameWorld gameWorld;
    private BallView ballView;

    @Override
    public void show() {
        //Box2D
        gameWorld = new GameWorld(this, WORLD_WIDTH, WORLD_HEIGHT);
        Gdx.input.setInputProcessor(gameWorld);
        debugRen = new Box2DDebugRenderer();

        loadAssets();
    }

    public void loadAssets() {
        //First Load
        //Ball Sprite
        if(!bka.assManager.isLoaded(ballSpritePath)) bka.assManager.load(ballSpritePath, Texture.class);

        //When finished loading set
        bka.assManager.finishLoading();
        //Ball Sprite
        //ballSprite = new Sprite(new Texture(ballSpritePath));
        Texture t = bka.assManager.get(ballSpritePath, Texture.class);
        ballSprite = new Sprite(bka.assManager.get(ballSpritePath, Texture.class));
        ballSprite.setCenter(-GameWorld.BALL_RADIUS_BOX / 2, -GameWorld.BALL_RADIUS_BOX / 2);
    }

    Vector3 ballScreenCords;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameWorld.updateWorld(Gdx.graphics.getDeltaTime());

        bka.spriteBatch.begin();
        gameWorld.renderWorld(bka.spriteBatch);
        bka.spriteBatch.end();

        Matrix4 cameraCopy = bka.camera.combined.cpy();
        debugRen.render(gameWorld.getWorld(), cameraCopy.scl(GameWorld.WORLD_TO_BOX));
    }
    @Override
    public void resize(int width, int height) {
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
    public void setBKA(BallKickerApp bka) {
        this.bka = bka;
    }
}
