package recursos;

import model.Elemento;
import model.EspacioTerrestre;
import common.Posicion;
import exceptions.FueraDeRangoException;
import exceptions.PosicionInvalidaException;

public class Cristal extends Elemento {

	public Cristal(int alto, int ancho, Posicion posicion) throws FueraDeRangoException, PosicionInvalidaException {
		super(alto, ancho, posicion, new EspacioTerrestre());
	}

	@Override
	public void posicionar(Posicion nuevaPosicion) throws FueraDeRangoException {
		// TODO Auto-generated method stub

	}

}
