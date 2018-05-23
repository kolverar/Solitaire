package mx.unam.poo.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.security.SecureRandom;

public class Baraja {

    private Carta baraja[];

    public Baraja(){

        baraja = new Carta[52];

        inicializarBaraja();
        inicializarTexturasBaraja();
        barajearBaraja();
    }

    private void inicializarBaraja(){

        String palosCartas[][] = {{"Corazon", "Rojo"}, {"Pica", "Negro"}, {"Diamante", "Rojo"}, {"Trebol", "Negro"}};
        int numeroBaraja = 0;

        for (String paloColor[] : palosCartas)
            for (int numeroCarta = 0; numeroCarta < 13; numeroCarta++)
                baraja[numeroBaraja++] = new Carta(null, 5, 365, numeroCarta + 1, paloColor[1], paloColor[0], true);
    }

    private void inicializarTexturasBaraja(){

        for (int i = 0; i < baraja.length; i++)
            baraja[i].setTexture(new Texture(Gdx.files.internal(baraja[i].getPalo() + "." + baraja[i].getNumero() + ".png")));
    }

    private void barajearBaraja(){

        SecureRandom secureRandom = new SecureRandom();
        int numeroRandom;
        Carta temporal;

        for (int i = 0; i < baraja.length; i++){

            do{

                numeroRandom = secureRandom.nextInt(baraja.length - 1);
            }while ((numeroRandom == i));

            temporal = baraja[i];
            baraja[i] = baraja[numeroRandom];
            baraja[numeroRandom] = temporal;
        }
    }

    public Carta getCarta(int i){

        return baraja[i];
    }
}
