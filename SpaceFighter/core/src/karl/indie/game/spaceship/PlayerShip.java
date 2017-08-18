package karl.indie.game.spaceship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import karl.indie.game.GameStage;
import karl.indie.game.spaceship.weapons.projectiles.StraightProjectile;

/**
 * Created by Karl on 24.07.2017.
 */
public class PlayerShip extends SpaceShip{


    private float sinceLastShot = 0f;
    private float shotsPerSec = 5f;
    private float secondsPerShot = 1f/20f;


    private final static float sqrtOfTwo = 1.414213f;
    private final static int DIRECTION_UP = 0;
    private final static int DIRECTION_RIGHT = 1;
    private final static int DIRECTION_DOWN = 2;
    private final static int DIRECTION_LEFT = 3;
    private boolean[] moveDirection = new boolean[4]; //0 -> UP, 1 -> RIGHT, 2 -> DOWN, 3 -> LEFT
    private float distance;
    private Vector2 moveVec;

    public PlayerShip() {
        super("Player");
        moveVec = new Vector2();
        playerName = "Player";
        shipSprite = new Sprite(new Texture(Gdx.files.internal("data/ship_blue.PNG")));

        speed = 100;
        setWidth(shipSprite.getWidth());
        setHeight(shipSprite.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sinceLastShot += delta;

        //Movement
        distance = speed * delta;
        moveVec.set(0, 0);
        moveDirection[DIRECTION_UP] = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
        moveDirection[DIRECTION_RIGHT] = Gdx.input.isKeyPressed(Input.Keys.E);//Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
        moveDirection[DIRECTION_DOWN] = Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
        moveDirection[DIRECTION_LEFT] = Gdx.input.isKeyPressed(Input.Keys.Q);//Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
        //If the movement is diagonal, reduce the distance to distance/sqrt(2)
        if(moveDirection[DIRECTION_UP] ^ moveDirection[DIRECTION_DOWN]
        && moveDirection[DIRECTION_LEFT] ^ moveDirection[DIRECTION_RIGHT]) {
            distance /= sqrtOfTwo;
        }
        if(moveDirection[DIRECTION_UP]) {
            moveVec.y += distance;
        }
        if(moveDirection[DIRECTION_DOWN]) {
            moveVec.y -= distance;
        }
        if(moveDirection[DIRECTION_RIGHT]) {
            moveVec.x += distance;
        }
        if(moveDirection[DIRECTION_LEFT]) {
            moveVec.x -= distance;
        }
        moveVec.rotate(-getRotation());
        moveBy(moveVec.x, moveVec.y);

        //Rotation
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) rotateBy(180 * delta);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) rotateBy(-180 * delta);

        //Shooting
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(sinceLastShot >= secondsPerShot) {
                sinceLastShot = 0f;
                StraightProjectile proj = new StraightProjectile(getX(), getY(), getRotation());
                proj.setTeamName(getTeamName());
                float rotCenterX = getX() + shipSprite.getOriginX();
                float rotCenterY = getY() + shipSprite.getOriginY();
                float x = -(proj.getWidth() / 2);
                float y = getHeight() * 1/3;
                Vector2 vc = new Vector2(x, y);
                vc.rotate(-getRotation());


                proj.setPosition(vc.x + rotCenterX, vc.y + rotCenterY);
                ((GameStage) getStage()).addProjectile(proj);
            }
        }
    }

    @Override
    public void die() {
        ((GameStage) getStage()).looseGame();
    }

    ShapeRenderer sr = new ShapeRenderer();

    float printed = 0f;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        /*
        batch.end();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(250, 10, 10, 0.3f);
        sr.circle(getX(), getY(), 5f);
        sr.end();
        batch.begin();*/
    }

}
