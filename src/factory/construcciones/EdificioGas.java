package factory.construcciones;

import common.Costo;
import common.Posicion;

import exceptions.CostoInvalidoException;
import exceptions.FueraDeRangoException;
import exceptions.PosicionInvalidaException;

public abstract class EdificioGas extends Edificio {
	
	private static final int CANTIDAD_DE_MINERAL = 5;

	public EdificioGas(int alto, int ancho, Posicion posicion)
			throws FueraDeRangoException, CostoInvalidoException, PosicionInvalidaException {
		super(alto, ancho, posicion);
		// TODO Auto-generated constructor stub
		this.setCosto(new Costo("100M"));
		this.setTiempoDeConstruccion(6);
	}

	public int getGas() {
		return CANTIDAD_DE_MINERAL;
	}

}
