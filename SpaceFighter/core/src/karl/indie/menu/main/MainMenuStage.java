package karl.indie.menu.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import karl.indie.SpaceFighterGame;

/**
 * Created by Karl on 26.07.2017.
 */
public class MainMenuStage extends Stage{

    private SpaceFighterGame sfg;
    private Skin skin;
    private Array<Button> buttons;

    public MainMenuStage(final SpaceFighterGame sfg) {
        super();
        this.sfg = sfg;
        buttons = new Array<Button>();

        skin = new Skin(Gdx.files.internal("data/uitest/uiskin.json"));

        Table table = new Table(skin);
        table.setDebug(true);
        table.setFillParent(true);


        final TextButton button = new TextButton("Start Game!", skin, "default");
        buttons.add(button);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Game Started");
                sfg.setMode(SpaceFighterGame.MODE_PLAY);
            }
        });

        final TextButton button1 = new TextButton("Exit!", skin, "default");
        buttons.add(button1);
        button1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
/*
        VerticalGroup vg = new VerticalGroup();
        vg.padTop(150);
        vg.
        vg.setFillParent(true);
        vg.addActor(button);
        vg.addActor(button1);
        addActor(vg);*/

        table.padTop(200);
        table.top();
        for(Button b : buttons) {
            table.add(b).width(250).pad(25);
            table.row();
        }

        addActor(table);
    }
}
