package karl.indie.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by Karl on 01.08.2017.
 */
public class HitBox {
    private Polygon poly;
    private float width, height;
    private float x, y;

    public HitBox(float x, float y, float width, float height) {
        poly = new Polygon();
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
