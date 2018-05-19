package mx.unam.poo.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;

public class SolitarioGame extends Solitario{

    private SpriteBatch batch;

    public SolitarioGame(){

        batch = new SpriteBatch();
    }

    public void imprimirMenu() {

        try {

            switch (solicitarDatosVentana(String.format("1) De mazo a fila %n2) De mazo a palo %n" +
                    "3) De fila a fila %n4) De fila a palo %n5) Siguiente carta del mazo"))){

                case 1:

                    mazoFila(solicitarDatosVentana("Ingrese el numero de fila") - 1);
                    break;

                case 2:

                    mazoPalo();
                    break;

                case 3:

                    filaFila(solicitarDatosVentana("Ingrese el numero de fila origen") - 1,
                            solicitarDatosVentana("Ingrese el numero de carta") - 1,
                            solicitarDatosVentana("Ingrese el numero de fila destino") - 1);
                    break;

                case 4:

                    filaPalo(solicitarDatosVentana("Ingrese el numero de fila") - 1);
                    break;

                case 5:

                    siguienteCartaMazo();
                    break;
            }
        }
        catch (NumberFormatException e){

            System.out.println("Error! " + e);
        }
        catch (ArrayIndexOutOfBoundsException e){

            System.out.println("Error! " + e);
        }
    }

    public int solicitarDatosVentana(String mensajeVentana){

        JOptionPane jOptionPane = new JOptionPane(mensajeVentana, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null);
        jOptionPane.setWantsInput(true);

        JDialog jDialog = jOptionPane.createDialog(null, "Menu del Solitario");
        jDialog.setLocation(550, 700);
        jDialog.setVisible(true);

        return Integer.parseInt((String) jOptionPane.getInputValue());
    }

    public void renderSolitario(){

        tablero.renderTablero();
    }

    public int ganarSolitario(){

        int contadorCartas = 0;

        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 20; j++)
                if (tablero.getCartaColumnas(j,i).getEstado())
                    contadorCartas++;

        contadorCartas += tablero.getCartaMazo("Pica", 0).getNumero();
        contadorCartas += tablero.getCartaMazo("Diamante", 0).getNumero();
        contadorCartas += tablero.getCartaMazo("Trebol", 0).getNumero();
        contadorCartas += tablero.getCartaMazo("Corazon", 0).getNumero();

        return contadorCartas;
    }

    public void renderGanarSolitario(){

        batch.begin();
        batch.draw(new Texture(Gdx.files.internal("ganaste.png")), 0, 0);
        batch.end();
    }
}
