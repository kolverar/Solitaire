package mx.unam.poo.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Carta {

    private Texture texture;
    private int coordX;
    private int coordY;
    private int numero;
    private String color;					// Rojo - Negro
    private String palo;                    // Corazon - Pica - Diamante - Trebol
    private boolean estado;

    public Carta (){

        texture = new Texture(Gdx.files.internal("carta.false.png"));
        coordX = 0;
        coordY = 0;
        numero = 0;
        color = null;
        palo = null;
        estado = false;
    }

    public Carta(Texture texture, int coordX, int coordY, int numero, String color, String palo, boolean estado) {

        this.texture = texture;
        this.coordX = coordX;
        this.coordY = coordY;
        this.numero = numero;
        this.color = color;
        this.palo = palo;
        this.estado = estado;
    }

    public Carta(int coordX, int coordY){

        texture = new Texture(Gdx.files.internal("carta.false.png"));
        this.coordX = coordX;
        this.coordY = coordY;
        numero = 0;
        color = null;
        palo = null;
        estado = false;
    }

    public void setTexture(Texture texture){

        this.texture = texture;
    }

    public void setCoordX(int coordX) {

        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {

        this.coordY = coordY;
    }

    public void setEstado(boolean estado) {

        this.estado = estado;
    }

    public Texture getTexture() {

        return texture;
    }

    public int getCoordX() {

        return coordX;
    }

    public int getCoordY() {

        return coordY;
    }

    public int getNumero() {

        return numero;
    }

    public String getColor() {

        return color;
    }

    public String getPalo() {

        return palo;
    }

    public boolean getEstado(){

        return estado;
    }

    @Override
    public String toString() {

        return "Numero: " + getNumero() + ", Color: " + getColor() + ", Palo: " + getPalo() + ", Estado: " + getEstado() +
                ", Coords: (" + coordX + "," + coordY + ")" ;
    }
}
