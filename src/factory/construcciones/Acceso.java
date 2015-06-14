package factory.construcciones;

import command.CrearZealotAccion;
import common.Costo;
import common.Posicion;
import common.Vitalidad;

import exceptions.CostoInvalidoException;
import exceptions.FueraDeRangoException;

public class Acceso extends Edificio {

	public Acceso(int alto, int ancho, Posicion posicion)
			throws FueraDeRangoException, CostoInvalidoException {
		super(alto, ancho, posicion);
		this.setCosto(new Costo("150M"));
		this.setTiempoDeConstruccion(8);
		this.setVitalidad(new Vitalidad(500,500));
		this.definirAccionesDisponibles();
	}
	
	public void definirAccionesDisponibles() {
		agregarAccionDisponible("Crear Zealot", new CrearZealotAccion(this));
	}

}