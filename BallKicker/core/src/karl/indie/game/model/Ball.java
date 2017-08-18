package karl.indie.game.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import karl.indie.game.control.GameWorld;

/**
 * Created by Karl on 17.08.2017.
 */
public class Ball {
    public float ballRadius;
    public float ballRadiusBox;
    public float ballRadiusBoxTimesTwo;

    private GameWorld gameWorld;

    public Body ballBody;
    public Fixture ballFixture;
    public CircleShape ballShape;

    public Ball(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        setRadius(1.5f);

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(gameWorld.getWidth() / 2, gameWorld.getHeight() * 3/4);
        ballBody = gameWorld.getWorld().createBody(def);

        ballShape = new CircleShape();
        ballShape.setRadius(ballRadius);
        ballFixture = ballBody.createFixture(ballShape, 1f);
        ballFixture.setRestitution(0.75f);

    }

    public void setRadius(float f) {
        ballRadius = f;
        ballRadiusBox = ballRadius * GameWorld.WORLD_TO_BOX;
        ballRadiusBoxTimesTwo = ballRadiusBox * 2;
    }

    public boolean hitBy(Vector2 testPoint) {
        if(ballFixture.testPoint(testPoint.x, testPoint.y)) {
            Vector2 moveVec = new Vector2();

            //Calc upwards force
            float yDist = ballBody.getPosition().y - testPoint.y;
            if(yDist < 0) {
                yDist *= -1;
            }
            moveVec.y = 1f * (-gameWorld.getWorld().getGravity().y) + 0.5f * (yDist/ballShape.getRadius() * (-gameWorld.getWorld().getGravity().y));

            //Calc horizontal force
            float xDist = ballBody.getPosition().x - testPoint.x;
            float random = MathUtils.random(1.5f, 3f);
            if(xDist < 0) {
                moveVec.x = -2f + 2f * (xDist/ballShape.getRadius());
                moveVec.x -= random;
            } else {
                moveVec.x = 2f + 2f * (xDist/ballShape.getRadius());
                moveVec.x += random;
            }

            ballBody.setLinearVelocity(moveVec);
        }

        return false;
    }
}
