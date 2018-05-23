package mx.unam.poo.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tablero {

    private Carta mazo[];
    private Carta columnasCartas[][];
    private Carta mazoPica;
    private Carta mazoDiamante;
    private Carta mazoTrebol;
    private Carta mazoCorazon;
    private SpriteBatch batch;
    private Texture wallpaper;

    public Tablero(){

        mazo = new Carta[24];
        columnasCartas = new Carta[20][7];
        batch = new SpriteBatch();
        wallpaper = new Texture(Gdx.files.internal("wallpaper.png"));

        inicializarTablero();
    }

    public void setCartaMazo(String palo, Carta carta, int i){

        if(palo.equals("Mazo"))
            mazo[i] = carta;
        else if (palo.equals("Pica"))
            mazoPica = carta;
        else if (palo.equals("Diamante"))
            mazoDiamante = carta;
        else if (palo.equals("Trebol"))
            mazoTrebol = carta;
        else if (palo.equals("Corazon"))
            mazoCorazon = carta;
    }

    public void setCartaColumnas(Carta carta, int i, int j){

        columnasCartas[i][j] = carta;
    }

    public Carta getCartaMazo(String palo, int i){

        if (palo.equals("Mazo"))
            return mazo[i];
        else if (palo.equals("Pica"))
            return mazoPica;
        else if (palo.equals("Diamante"))
            return mazoDiamante;
        else if (palo.equals("Corazon"))
            return mazoCorazon;
        else if (palo.equals("Trebol"))
            return mazoTrebol;
        else return null;
    }

    public Carta getCartaColumnas(int i, int j){

        return columnasCartas[i][j];
    }

    private void inicializarTablero(){

        Baraja baraja = new Baraja();

        inicializarMazo(baraja);
        inicializarMazosPalos();
        inicializarColumnas(baraja);
    }

    private void inicializarMazo(Baraja baraja){

        for (int i = 0; i < 24; i++)
            mazo[i] = baraja.getCarta(i);
    }

    private void inicializarMazosPalos(){

        mazoPica = new Carta(null,551,365, 0, "Negro", "Pica", true);
        mazoDiamante = new Carta( null,460,365, 0, "Rojo", "Diamante", true);
        mazoTrebol = new Carta( null,369,365, 0, "Negro", "Trebol", true);
        mazoCorazon = new Carta( null,278,365, 0, "Rojo", "Corazon", true);

        mazoPica.setTexture(new Texture(Gdx.files.internal(mazoPica.getPalo() + "." + mazoPica.getNumero() + ".png")));
        mazoDiamante.setTexture(new Texture(Gdx.files.internal(mazoDiamante.getPalo() + "." + mazoDiamante.getNumero() + ".png")));
        mazoTrebol.setTexture(new Texture(Gdx.files.internal(mazoTrebol.getPalo() + "." + mazoTrebol.getNumero() + ".png")));
        mazoCorazon.setTexture(new Texture(Gdx.files.internal(mazoCorazon.getPalo() + "." + mazoCorazon.getNumero() + ".png")));
    }

    private void inicializarColumnas(Baraja baraja){

        int coordX = -86;
        int coordY;

        for (int i = 0; i < 7; i++) {

            coordX += 91;
            coordY = 250;
            for (int j = 0; j < 20; j++) {

                columnasCartas[j][i] = new Carta(coordX, coordY);
                coordY -= 20;
            }
        }

        inicializarCartasColumnas(baraja);
        inicializarEstadoCartas();
    }

    private void inicializarCartasColumnas(Baraja baraja){

        int numeroCarta = 24;

        for (int i = 0; i < 7; i++)
            for (int j = 0; j < i + 1; j++)
                columnasCartas[j][i] = remplazarCarta(columnasCartas[j][i], baraja.getCarta(numeroCarta++));
    }

    private void inicializarEstadoCartas(){

        for (int i = 1; i < 7; i++)
            for (int j = 0; j < i; j++)
                columnasCartas[j][i].setEstado(false);
    }

    public int posicionUltimaCarta(int fila){

        int posicion = -1;

        for (int i = 0; i < 20 ; i++)
            if (columnasCartas[i][fila].getNumero() != 0)
                posicion++;

        return posicion;
    }

    private Carta remplazarCarta(Carta cartaAnterior, Carta cartaPosterior){

        return new Carta(cartaPosterior.getTexture(), cartaAnterior.getCoordX(), cartaAnterior.getCoordY(), cartaPosterior.getNumero(),
                cartaPosterior.getColor(), cartaPosterior.getPalo(), cartaPosterior.getEstado());
    }

    public void renderTablero(){

        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(wallpaper, 0, 0);
        renderMazos();
        renderColumnas();
        batch.end();
    }

    private void renderMazos(){

        batch.draw(mazo[0].getTexture(), mazo[0].getCoordX(), mazo[0].getCoordY());
        batch.draw(mazoCorazon.getTexture(), mazoCorazon.getCoordX(), mazoCorazon.getCoordY());
        batch.draw(mazoTrebol.getTexture(), mazoTrebol.getCoordX(), mazoTrebol.getCoordY());
        batch.draw(mazoDiamante.getTexture(), mazoDiamante.getCoordX(), mazoDiamante.getCoordY());
        batch.draw(mazoPica.getTexture(), mazoPica.getCoordX(), mazoPica.getCoordY());
    }

    private void renderColumnas(){

        Texture cartaFalse = new Texture(Gdx.files.internal("carta.false.png"));

        for (int i = 0; i < 7; i++)
            for (int j = 0; j < posicionUltimaCarta(i) + 1; j++)
                if (columnasCartas[j][i].getEstado())
                    batch.draw(columnasCartas[j][i].getTexture(), columnasCartas[j][i].getCoordX(), columnasCartas[j][i].getCoordY());
                else
                    batch.draw(cartaFalse, columnasCartas[j][i].getCoordX(), columnasCartas[j][i].getCoordY());
    }
}
