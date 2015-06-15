package common;

import exceptions.FueraDeRangoException;
import exceptions.PosicionInvalidaException;

public class Posicion {
	private int posX;
	private int posY;
	
	public Posicion(int posX, int posY) throws FueraDeRangoException, PosicionInvalidaException{
		
		if( posX>Constantes.ANCHO_DEFECTO||posY>Constantes.ALTO_DEFECTO||posX<0||posY<0)
			throw new FueraDeRangoException(Mensajes.MSJ_ERROR_FUERA_DE_RANGO);
	
		this.setPosX(posX);
		this.setPosY(posY);

	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public boolean equals(Object pos){
		return (this.posX ==  ((Posicion) pos).getPosX() && this.posY == ((Posicion) pos).getPosY());
	}

	public int getDistancia(Posicion otraPosicion) {
		
		double restaEnX = (otraPosicion.getPosX() - this.getPosX());
		double restaEnY = (otraPosicion.getPosY() - this.getPosY()); 
		
		int distancia = (int) Math.sqrt(Math.pow(restaEnX, 2)+Math.pow(restaEnY,2));
		
		return distancia;
	}

	
}
