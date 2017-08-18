package karl.indie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import karl.indie.SpaceFighterGame;
import karl.indie.game.spaceship.weapons.projectiles.Projectile;
import karl.indie.game.spaceship.weapons.projectiles.StraightProjectile;
import karl.indie.game.spaceship.PlayerShip;
import karl.indie.game.spaceship.SpaceShip;
import karl.indie.game.spaceship.npc.BasicNPC;

/**
 * Created by Karl on 24.07.2017.
 */
public class GameStage extends Stage {
    private SpaceFighterGame sfg;

    private Group projectiles;
    private PlayerShip playerShip;
    private Group spaceShips;

    private Array<Projectile> toRemove;
    private Projectile currProj;
    private Polygon currProjHitBox;
    private Actor currShip;

    public GameStage(SpaceFighterGame sfg) {
        super();
        this.sfg = sfg;

        projectiles = new Group();
        addActor(projectiles);
        toRemove = new Array<Projectile>();
        spaceShips = new Group();
        addActor(spaceShips);


        playerShip = new PlayerShip();
        playerShip.setPosition(getWidth() / 2, getHeight() / 2);
        spaceShips.addActor(playerShip);
    }

    private float sinceMouseFire = 0f;
    private float sinceCollCheck = 0f;

    private float sinceNpcAdded = 5f;
    @Override
    public void act(float delta) {
        super.act(delta);
        sinceNpcAdded += delta;
        if (sinceNpcAdded >= 5f) {
            BasicNPC e = new BasicNPC();
            e.rotateBy(90);
            addSpaceShip(e);
            sinceNpcAdded = 0;
        }

        //Collision detection only every 0,1s
        sinceCollCheck += delta;
        if(projectiles.getChildren().size >= 0 && sinceCollCheck >= 0.1) checkCollision();

        if(Gdx.input.isKeyPressed(Input.Keys.B) && sinceMouseFire >= 0.3) {
            System.out.println(playerShip.getX() + "   " + playerShip.getY());
            System.out.println(playerShip.shipSprite.getX() + "   " + playerShip.shipSprite.getY());
            sinceMouseFire = 0;
        }

        sinceMouseFire += delta;
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if(sinceMouseFire >= 0.3) {
                Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                getCamera().unproject(v);
                Projectile proj = new StraightProjectile(v.x, v.y, 270);
                proj.damage = 10f;
                addProjectile(proj);
                sinceMouseFire = 0;
            }
        }

    }

    public void addProjectile(Projectile p) {
        boolean added = false;
        for (int i = 0; i < projectiles.getChildren().size; i++) {
            Actor checkingActor = projectiles.getChildren().get(i);
            if(checkingActor.getName() == p.getName() && checkingActor instanceof Group) {
                ((Group)projectiles.getChildren().get(i)).addActor(p);
                added = true;
            }
        }
        if(!added) {
            Group newGroup = new Group();
            newGroup.setName(p.getTeamName());
            newGroup.addActor(p);
            projectiles.addActor(newGroup);
        }
    }

    public void addSpaceShip(SpaceShip spaceShip) {
        boolean added = false;
        for (int i = 0; i < spaceShips.getChildren().size; i++) {
            Actor checkingActor = spaceShips.getChildren().get(i);
            if(checkingActor.getName() == spaceShip.getName() && checkingActor instanceof Group) {
                ((Group)spaceShips.getChildren().get(i)).addActor(spaceShip);
                added = true;
            }
        }
        if(!added) {
            Group newGroup = new Group();
            newGroup.setName(spaceShip.getTeamName());
            spaceShips.addActor(newGroup);
        }
    }

    public void looseGame() {
        sfg.setMode(SpaceFighterGame.MODE_MAIN_MENU);
    }

    private void checkCollision() {
        for(int h = 0; h < projectiles.getChildren().size; h++) {
            Group currProjGroup = (Group) projectiles.getChildren().get(h);
            for (int i = 0; i < currProjGroup.getChildren().size; i++) {
                currProj = (Projectile) currProjGroup.getChildren().get(i);
                currProjHitBox = currProj.hitBox;
                for (int j = 0; j < spaceShips.getChildren().size; j++) {
                    //Can be group, only PlayerShip is a single Actor
                    currShip = spaceShips.getChildren().get(j);
                    if (currShip.getName() != currProj.getName()) {
                        //System.out.println(currShip.getName() + "  " + currProj.getName() + "  " + currProjHitBox.overlaps(playerShip.hitBox));
                        if (currShip.getName() == "Player" && currProj.getName() != "Player" && currProjHitBox.contains(playerShip.hitBox.getVertices()[0], playerShip.hitBox.getVertices()[1])) {
                            toRemove.add(currProj);
                            playerShip.hitBy(currProj);
                        } else if (currShip instanceof Group) {
                            Group currShipGroup = (Group) currShip;
                            for (int k = 0; k < currShipGroup.getChildren().size; k++) {
                                SpaceShip shipToHit = (SpaceShip) currShipGroup.getChildren().get(k);
                                if (currProjHitBox.contains(playerShip.hitBox.getVertices()[0], playerShip.hitBox.getVertices()[1])) {
                                    shipToHit.hitBy(currProj);
                                    toRemove.add(currProj);
                                }
                            }
                        }
                    }
                }
            }
        }

        for(Projectile p : toRemove) p.remove();
        currProj = null;
        currProjHitBox = null;
        currShip = null;
        toRemove.clear();

        sinceCollCheck = 0;
    }
}
