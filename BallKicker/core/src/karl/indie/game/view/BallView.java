package karl.indie.game.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import karl.indie.game.control.GameWorld;
import karl.indie.game.model.Ball;

/**
 * Created by Karl on 17.08.2017.
 */
public class BallView {

    private boolean ballSpriteSizedAndPositioned = false;

    private Ball ball;
    private GameWorld gw;

    public Sprite ballSprite;
    public static final String ballSpritePath = "data/SoccerBall.png";

    public BallView(GameWorld gw, Ball ball) {
        this.gw = gw;
        this.ball = ball;
    }

    public void draw(SpriteBatch spriteBatch) {
        //First size ball
        if(!ballSpriteSizedAndPositioned) {
            Vector2 vec = gw.gs.bka.viewport.project(new Vector2(0,ball.ballRadiusBoxTimesTwo));
            ballSprite.setSize(vec.y, vec.y);
            ballSprite.setOrigin(vec.y / 2, vec.y / 2);
            ballSpriteSizedAndPositioned = true;
        }

        Vector2 vec = new Vector2();
        vec.x = (ball.ballBody.getPosition().x - ball.ballRadius) * GameWorld.WORLD_TO_BOX;
        vec.y = (ball.ballBody.getPosition().y - ball.ballRadius) * GameWorld.WORLD_TO_BOX;
        vec = gw.gs.bka.viewport.project(vec);
        ballSprite.setPosition(vec.x, vec.y);

        ballSprite.setRotation(ball.ballBody.getAngle() * MathUtils.radiansToDegrees);

        ballSprite.draw(spriteBatch);
    }

}
