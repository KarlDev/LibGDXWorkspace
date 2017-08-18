package karl.indie.game.spaceship.weapons.slots;

import com.badlogic.gdx.scenes.scene2d.Actor;
import karl.indie.game.spaceship.SpaceShip;
import karl.indie.game.spaceship.weapons.weapons.Weapon;

/**
 * Created by Karl on 01.08.2017.
 */
public class WeaponSlot extends Actor{
    private SpaceShip ship;
    private Weapon weapon;

    private float fromOriginX;
    private float fromOriginY;

    private String sizeRating;
    private String qualityRating;


    public WeaponSlot(float fromOriginX, float fromOriginY, float initialRotation) {
        this.fromOriginX = fromOriginX;
        this.fromOriginY = fromOriginY;
        this.setRotation(initialRotation);

    }

    public void setShip(SpaceShip ship) {
        this.ship = ship;
    }

    public float getFromOriginX() {
        return fromOriginX;
    }

    public void setFromOriginX(float fromOriginX) {
        this.fromOriginX = fromOriginX;
    }

    public float getFromOriginY() {
        return fromOriginY;
    }

    public void setFromOriginY(float fromOriginY) {
        this.fromOriginY = fromOriginY;
    }public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getSizeRating() {
        return sizeRating;
    }

    public void setSizeRating(String sizeRating) {
        this.sizeRating = sizeRating;
    }

    public String getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(String qualityRating) {
        this.qualityRating = qualityRating;
    }
}
