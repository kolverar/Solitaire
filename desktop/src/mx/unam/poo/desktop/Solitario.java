package mx.unam.poo.desktop;

import org.jetbrains.annotations.NotNull;

public abstract class Solitario {

    protected Tablero tablero;

    public Solitario(){

        tablero = new Tablero();
    }

    public void mazoFila(int filaDestino){

        // Copia de la carta del mazo (mazo[0])
        Carta cartaMazo = tablero.getCartaMazo("Mazo", 0);

        if (tablero.posicionUltimaCarta(filaDestino) != -1){

            // Copia de la ultima carta de la fila
            Carta cartaFila = tablero.getCartaColumnas(tablero.posicionUltimaCarta(filaDestino), filaDestino);

            if(cartaMazo.getColor() != cartaFila.getColor() && cartaMazo.getNumero() == cartaFila.getNumero() - 1)
                moverMazoFila(cartaMazo, tablero.getCartaColumnas(tablero.posicionUltimaCarta(filaDestino) + 1, filaDestino),
                        tablero.posicionUltimaCarta(filaDestino) + 1, filaDestino);

        }else if (cartaMazo.getNumero() == 13)

            // tablero.getCartaColumnas(0, filaDestino) es la copia de la carta oculta en la primera posicion
            moverMazoFila(cartaMazo, tablero.getCartaColumnas(0, filaDestino), 0, filaDestino);
    }

    public void mazoPalo(){

        // Copia de la carta del mazo (mazo[0])
        Carta cartaMazo = tablero.getCartaMazo("Mazo", 0);
        // Copia de la carta en el mazo del palo
        Carta cartaPalo = tablero.getCartaMazo(cartaMazo.getPalo(), 0);

        // Numero de la carta del mazo == Numero carta del mazo del palo + 1
        if (cartaMazo.getNumero() == cartaPalo.getNumero() + 1 ){

            //Movemos la carta del mazo a la fila del palo
            tablero.setCartaMazo(cartaMazo.getPalo(), remplazarCarta(cartaPalo, cartaMazo),0);
            //Eliminamos la carta del mazo
            tablero.setCartaMazo("Mazo", remplazarCarta(cartaMazo, new Carta()),0);
            //Siguiente carta del mazo
            siguienteCartaMazo();
        }
    }

    private void moverMazoFila(Carta cartaMazo, Carta cartaFila, int numeroFilaDestino, int filaDestino){

        //Movemos la carta del mazo a la ultima posicion de la fila
        tablero.setCartaColumnas(remplazarCarta(cartaFila, cartaMazo), tablero.posicionUltimaCarta(filaDestino) + 1, filaDestino);
        //Eliminamos la carta del mazo
        tablero.setCartaMazo("Mazo", remplazarCarta(cartaMazo, new Carta()),0);
        //Siguiente carta del mazo
        siguienteCartaMazo();
    }

    public void filaFila(int filaOrigen, int numeroFilaOrigen, int filaDestino){

        // Copia de la carta seleccionada de la fila origen
        Carta cartaFilaOrigen = tablero.getCartaColumnas(numeroFilaOrigen, filaOrigen);

        if (tablero.posicionUltimaCarta(filaDestino) != -1) {

            // Copia de la ultima carta de la fila destino
            Carta cartaFilaDestino = tablero.getCartaColumnas(tablero.posicionUltimaCarta(filaDestino), filaDestino);
            // Color de la carta origen != Color de la carta destino
            if (cartaFilaOrigen.getEstado() && cartaFilaOrigen.getColor() != cartaFilaDestino.getColor() && cartaFilaOrigen.getNumero() == cartaFilaDestino.getNumero() - 1)
                moverCartasFilas(filaOrigen, numeroFilaOrigen, filaDestino);

        }else if (cartaFilaOrigen.getEstado() && cartaFilaOrigen.getNumero() == 13){

            moverCartasFilas(filaOrigen, numeroFilaOrigen, filaDestino);
        }
    }

    public void filaPalo (int filaOrigen){

        if(tablero.posicionUltimaCarta(filaOrigen) != -1) {

            // Copia de la ultima carta de la fila origen
            Carta cartaFila = tablero.getCartaColumnas(tablero.posicionUltimaCarta(filaOrigen), filaOrigen);
            // Copia de la carta en el mazo del palo
            Carta cartaPalo = tablero.getCartaMazo(cartaFila.getPalo(), 0);

            if (cartaFila.getNumero() == cartaPalo.getNumero() + 1) {

                //Movemos la carta de la fila al mazo del palo
                tablero.setCartaMazo(cartaPalo.getPalo(), remplazarCarta(cartaPalo, cartaFila), 0);
                verificarCartaAnterior(filaOrigen, tablero.posicionUltimaCarta(filaOrigen));
                eliminarCartas(filaOrigen, tablero.posicionUltimaCarta(filaOrigen));
            }
        }
    }

    public void siguienteCartaMazo(){

        Carta mazo[] = new Carta[24];
        int cartasMazo;
        Carta temporal;

        // Inicializacion del arreglo
        for(int i = 0; i < 24; i++)
            mazo[i] = new Carta(5, 365);

        // Recorremos a la izquierda el arreglo
        temporal = tablero.getCartaMazo("Mazo", 0);
        for(int i = 1; i < 24; i++)
            tablero.setCartaMazo("Mazo", tablero.getCartaMazo("Mazo", i), i - 1);
        tablero.setCartaMazo("Mazo", temporal, 23);

        // Evitamos espacios en el arreglo
        cartasMazo = 0;
        for(int i = 0; i < 24; i++)
            if (tablero.getCartaMazo("Mazo", i).getNumero() != 0)
                mazo[cartasMazo++] = tablero.getCartaMazo("Mazo", i);

        // Copiamos el arreglo al mazo
        for(int i = 0; i < 24; i++)
            tablero.setCartaMazo("Mazo", mazo[i], i);

        System.out.println("Cartas en el mazo: " + cartasMazo);
    }

    private void moverCartasFilas(int filaOrigen, int numeroFilaOrigen, int filaDestino){

        verificarCartaAnterior(filaOrigen, numeroFilaOrigen);
        copiarCartas(filaOrigen, numeroFilaOrigen, filaDestino);
        eliminarCartas(filaOrigen, numeroFilaOrigen);
    }

    private void copiarCartas(int filaOrigen, int numeroFilaOrigen, int filaDestino) {

        Carta cartaCopiar;

        if (tablero.posicionUltimaCarta(filaDestino) != -1) {

            int posicionUltimaCartaFilaDestino = tablero.posicionUltimaCarta(filaDestino);

            for (int i = numeroFilaOrigen; i < 20; i++) {

                cartaCopiar = tablero.getCartaColumnas(i, filaOrigen);
                posicionUltimaCartaFilaDestino++;
                if (cartaCopiar.getNumero() != 0)
                    tablero.setCartaColumnas(remplazarCarta(tablero.getCartaColumnas(posicionUltimaCartaFilaDestino, filaDestino), cartaCopiar), posicionUltimaCartaFilaDestino, filaDestino);

            }
        } else {

            int numeroCartaDestino = -1;

            for (int i = numeroFilaOrigen; i < 20; i++) {

                cartaCopiar = tablero.getCartaColumnas(i, filaOrigen);
                numeroCartaDestino++;
                if (cartaCopiar.getNumero() != 0)
                    tablero.setCartaColumnas(remplazarCarta(tablero.getCartaColumnas(numeroCartaDestino, filaDestino), cartaCopiar), numeroCartaDestino, filaDestino);

            }
        }
    }

    private void verificarCartaAnterior(int filaOrigen, int numeroFilaOrigen){

        if(numeroFilaOrigen > 0) {
            Carta cartaVerificacion = tablero.getCartaColumnas(numeroFilaOrigen - 1, filaOrigen);

            tablero.setCartaColumnas(new Carta(cartaVerificacion.getTexture(), cartaVerificacion.getCoordX(), cartaVerificacion.getCoordY(),
                    cartaVerificacion.getNumero(), cartaVerificacion.getColor(), cartaVerificacion.getPalo(), true),
                    numeroFilaOrigen - 1, filaOrigen);
        }
    }

    private void eliminarCartas(int filaOrigen, int numeroFilaOrigen){

        Carta cartaAnterior;

        for (int i = numeroFilaOrigen; i < 20; i++){
            cartaAnterior = tablero.getCartaColumnas(i, filaOrigen);
            tablero.setCartaColumnas(remplazarCarta(cartaAnterior, new Carta()), i, filaOrigen);

        }
    }

    @NotNull
    private Carta remplazarCarta(Carta cartaAnterior, Carta cartaPosterior){

        return new Carta(cartaPosterior.getTexture(), cartaAnterior.getCoordX(), cartaAnterior.getCoordY(), cartaPosterior.getNumero(),
                cartaPosterior.getColor(), cartaPosterior.getPalo(), cartaPosterior.getEstado());
    }
}