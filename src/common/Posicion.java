package common;

import titiritero.modelo.ObjetoPosicionable;
import exceptions.FueraDeRangoException;
import exceptions.PosicionInvalidaException;

public class Posicion implements ObjetoPosicionable {
	private int posX;
	private int posY;
	private static int tamanioDePosicionX = 0;
	private static int tamanioDePosicionY = 0;
	
	public Posicion(int posX, int posY) throws FueraDeRangoException, PosicionInvalidaException{
		
		if( posX>Constantes.ANCHO_DEFECTO||posY>Constantes.ALTO_DEFECTO||posX<0||posY<0)
			throw new FueraDeRangoException(Mensajes.MSJ_ERROR_FUERA_DE_RANGO);
	
		this.setPosX(posX);
		this.setPosY(posY);

	}

	public int getX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public boolean equals(Object pos){
		return (this.posX ==  ((Posicion) pos).getX() && this.posY == ((Posicion) pos).getY());
	}

	public int getDistancia(Posicion otraPosicion) {
		
		double restaEnX = (otraPosicion.getX() - this.getX());
		double restaEnY = (otraPosicion.getY() - this.getY()); 
		
		int distancia = (int) Math.sqrt(Math.pow(restaEnX, 2)+Math.pow(restaEnY,2));
		
		return distancia;
	}
	
	public static void setTamanioDePosicionX(int tamanio) {
		tamanioDePosicionX = tamanio;
	}
	
	public static void setTamanioDePosicionY(int tamanio) {
		tamanioDePosicionY = tamanio;
	}



	
}
