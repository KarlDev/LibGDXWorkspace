package karl.indie.game.control;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import karl.indie.game.GameScreen;
import karl.indie.game.model.Ball;
import karl.indie.game.view.BallView;

/**
 * Created by Karl on 02.08.2017.
 */
public class GameWorld implements InputProcessor {
    public static final float BOX_TO_WORLD = 0.01f;
    public static final float WORLD_TO_BOX = 100f;
    public GameScreen gs;

    private float width, height;

    private World world;
    private Body[] worldBox;
    private Fixture[] worldBoxFixtures;
    private Ball ball;

    private BallView ballView;

    private float timePassed = 0f;
    public GameWorld(GameScreen gs, float width, float height) {
        this.gs = gs;
        this.width = width * BOX_TO_WORLD;
        this.height = height * BOX_TO_WORLD;
        world = new World(new Vector2(0, -9.8f), false);

        createWorldBox();
        ball = createBall();

        ballView = new BallView()


        createCollisionListener();
    }

    float test = 0;
    public void updateWorld(float delta) {
        timePassed += delta;
        world.step(delta, 6, 2);
        //test += delta;
        if(test >= 0.1) {
        }
    }

    public void renderWorld(SpriteBatch spriteBatch) {

    }

    public void createWorldBox() {
        worldBox = new Body[4];
        worldBoxFixtures = new Fixture[4];
        for(int i = 0; i < 4; i++) {
            Body b;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.KinematicBody;
            def.gravityScale = 0f;
            b = world.createBody(def);
            worldBox[i] = b;

            EdgeShape es = new EdgeShape();
            Vector2 posVecOrigin = new Vector2();
            Vector2 posVecDest = new Vector2();
            switch (i) {
                case 0:
                    posVecOrigin.set(0, 0);
                    posVecDest.set(width, 0);
                    break;
                case 1:
                    posVecOrigin.set(width, 0);
                    posVecDest.set(width, height);
                    break;
                case 2:
                    posVecOrigin.set(width, height);
                    posVecDest.set(0, height);
                    break;
                case 3:
                    posVecOrigin.set(0, height);
                    posVecDest.set(0, 0);
                    break;
            }
            es.set(posVecOrigin, posVecDest);
            worldBoxFixtures[i] = b.createFixture(es, 0f);
        }
    }

    private Ball createBall() {
        return new Ball(this);
    }

    Vector3 testPoint3 = new Vector3();
    Vector2 testPoint = new Vector2();
    Vector2 moveVec = new Vector2();

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        testPoint3.set(screenX, screenY, 0);
        gs.bka.viewport.unproject(testPoint3);
        testPoint.set(testPoint3.x * GameWorld.BOX_TO_WORLD, testPoint3.y * GameWorld.BOX_TO_WORLD);

        if(testPoint3.y <= height * 3/5 * WORLD_TO_BOX && ball.hitBy(testPoint)) {
            //ball hit

            return true;
        } else {
            //ball not hit

            return false;
        }
    }

    private void createCollisionListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fA = contact.getFixtureA();
                Fixture fB = contact.getFixtureB();
                if((fA == ball.ballFixture || fB == ball.ballFixture) && (fA == worldBoxFixtures[0] || fB == worldBoxFixtures[0])) {
                    //Game lost
                }
            }
            @Override
            public void endContact(Contact contact) {

            }
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    public World getWorld() {
        return world;
    }
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }
}
