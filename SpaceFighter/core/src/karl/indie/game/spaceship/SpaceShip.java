package karl.indie.game.spaceship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import karl.indie.game.spaceship.weapons.projectiles.Projectile;
import karl.indie.game.spaceship.weapons.slots.WeaponSlot;


/**
 * Created by Karl on 24.07.2017.
 */
public class SpaceShip extends Group {
    private Group weaponSlots;

    public static int computerPlayerCount = 0;
    public String teamName;
    public String playerName;

    //100 HP for testing
    public float maxHealth = 100f;
    public float currentHealth = 100f;

    //Texture
    public Sprite shipSprite = new Sprite(new Texture(Gdx.files.internal("data/i_are_spaceship.png")));

    public Polygon hitBox;
    public float speed = 1;

    public SpaceShip(String teamName) {
        super();
        setTeamName(teamName);

        hitBox = new Polygon(new float[]{0, 0, shipSprite.getWidth(), 0, shipSprite.getWidth(), shipSprite.getHeight(), 0, shipSprite.getHeight()});
    }

    public void addWeaponSlot(WeaponSlot slot) {
        weaponSlots.addActor(slot);
    }

    public void hitBy(Projectile p) {
        currentHealth -= p.damage;
        if(currentHealth <= 0) die();
    }

    public void die() {
        remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        hitBox.setPosition(getX(), getY());
        shipSprite.setPosition(getX(), getY());
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        hitBox.setVertices(new float[]{getX(), getY(), getX() + getWidth(), getY(),
                                        getX() + getWidth(), getY() + getHeight(),
                                        getX(), getY() + getHeight()});
    }


    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        if(getRotation() >= 360) {
            super.setRotation(0);
            shipSprite.setRotation(0);
            hitBox.setRotation(0);
        } else {
            shipSprite.setRotation(-getRotation());
            hitBox.setRotation(-getRotation());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        shipSprite.draw(batch);
    }

    @Override
    public boolean remove() {
        //hitBox.set(0, 0, 0, 0);
        return super.remove();
    }

    public void setTeamName(String teamName) {
        setName(teamName);
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}
