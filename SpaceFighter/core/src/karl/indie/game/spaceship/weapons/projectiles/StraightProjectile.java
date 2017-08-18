package karl.indie.game.spaceship.weapons.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Karl on 24.07.2017.
 */
public class StraightProjectile extends Projectile {

    public StraightProjectile(float x, float y, float directionDeg) {
        super();
        setPosition(x, y);
        setRotation(directionDeg);

        Vector2 vc = new Vector2(0, speed);
        vc.rotate(-directionDeg);

        addAction(repeat(RepeatAction.FOREVER, Actions.moveBy(vc.x, vc.y, 1f)));
    }
}
