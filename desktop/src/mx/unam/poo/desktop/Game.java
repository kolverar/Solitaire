package mx.unam.poo.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Game extends com.badlogic.gdx.Game {

    private SolitarioGame solitario;

    public void create() {

        solitario = new SolitarioGame();
    }

    public void render(){

        solitario.renderSolitario();

        if (solitario.ganarSolitario() == 52)
            solitario.renderGanarSolitario();

        if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){

            if(solitario.ganarSolitario() == 52)
                solitario = new SolitarioGame();

            solitario.imprimirMenu();
        }
    }
}
