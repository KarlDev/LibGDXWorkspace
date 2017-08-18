package karl.indie.game.spaceship.weapons.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


/**
 * Created by Karl on 24.07.2017.
 */
public class Projectile extends Actor{
    Sprite sprite = new Sprite(new Texture("data/laserschuss.png"));

    public String teamName = "Comp";
    public String originPlayer;
    public float speed = 200f;

    //5 dmg for testing
    public float damage = 5f;

    public Polygon hitBox;

    public Projectile() {
        super();
        setTeamName("Computer");
        sprite.setOrigin(0, 0);
        hitBox = new Polygon(new float[]{0, 0, sprite.getWidth(), 0, sprite.getWidth(), sprite.getHeight(), 0, sprite.getHeight()});
        setSize(sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getX() <= 0 || getY() <= 0 || getX() > getStage().getWidth() || getY() > getStage().getHeight()) {
            remove();
        }
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        hitBox.setPosition(getX(), getY());
        sprite.setPosition(getX(), getY());
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        if(getRotation() >= 360) {
            super.setRotation(0);
            sprite.setRotation(0);
            hitBox.setRotation(0);
        } else {
            sprite.setRotation(-getRotation());
            hitBox.setRotation(-getRotation());
        }
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        hitBox.setVertices(new float[]{getX(), getY(), getX() + getWidth(), getY(),
                getX() + getWidth(), getY() + getHeight(),
                getX(), getY() + getHeight()});
    }

    ShapeRenderer sr = new ShapeRenderer();
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }

    @Override
    public boolean remove() {
        hitBox.setVertices(new float[]{});
        return super.remove();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        setName(teamName);
        this.teamName = teamName;
    }

    public String getOriginPlayer() {
        return originPlayer;
    }

    public void setOriginPlayer(String originPlayer) {
        this.originPlayer = originPlayer;
    }
}
