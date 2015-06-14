package factory.construcciones;

import command.CrearGolliatAccion;
import common.Costo;
import common.Posicion;
import common.Vitalidad;

import exceptions.CostoInvalidoException;
import exceptions.FueraDeRangoException;

public class Fabrica extends Edificio {

	public Fabrica(int alto, int ancho, Posicion posicion)
			throws FueraDeRangoException, CostoInvalidoException {
		super(alto, ancho, posicion);
		this.setCosto(new Costo("200M100G"));
		this.setTiempoDeConstruccion(12);
		this.setVitalidad(new Vitalidad(1250,0));
		this.definirAccionesDisponibles();
	}
	
	public void definirAccionesDisponibles() {
		agregarAccionDisponible("Crear Golliat", new CrearGolliatAccion(this));
	}
	
}