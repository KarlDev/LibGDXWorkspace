package karl.indie.game.spaceship.npc;

import com.badlogic.gdx.math.MathUtils;
import static  com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import karl.indie.game.spaceship.SpaceShip;

/**
 * Created by Karl on 29.07.2017.
 */
public class BasicNPC extends SpaceShip{
    private boolean init;
    public BasicNPC() {
        super("Computer");
        playerName = "Computer" + computerPlayerCount;
        computerPlayerCount++;
        init = true;
        setVisible(false);
    }

    private void init() {
        setPosition(getStage().getWidth() - getWidth(), MathUtils.random(0, getStage().getHeight()));
        setRotation(270);
        speed = 100;
        addAction(forever(Actions.moveBy(-speed, 0, 1f)));

        setVisible(true);
        init = false;
    }
    @Override
    public void act(float delta) {
        if (init) init();
        super.act(delta);
        if(getX() <= 0) die();
    }
}
