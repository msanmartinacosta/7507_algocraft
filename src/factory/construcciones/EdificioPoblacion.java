package factory.construcciones;

import common.Costo;
import common.Posicion;
import exceptions.CostoInvalidoException;
import exceptions.FueraDeRangoException;
import exceptions.PosicionInvalidaException;
import model.Juego;

public abstract class EdificioPoblacion extends Edificio{
	
	private static final int CANTIDAD_DE_POBLACION = 5;

	public EdificioPoblacion(int alto, int ancho, Posicion posicion)
			throws FueraDeRangoException, CostoInvalidoException, PosicionInvalidaException {
		super(alto, ancho, posicion);
		this.setCosto(new Costo("100M"));
	}
	
	public int aumentoPoblacion() {
		return CANTIDAD_DE_POBLACION;
	}


	@Override
	public void disminuirPoblacion() {
		Juego.getInstancia().getJugadorEnemigo().disminuirPoblacionDisponble(this.getSuministro());
		
	}

}
