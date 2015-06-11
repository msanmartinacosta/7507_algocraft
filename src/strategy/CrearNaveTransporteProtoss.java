package strategy;

import model.ElementoArtificial;

import common.Posicion;

import controller.JuegoController;
import exceptions.ElementoInvalidoException;
import exceptions.FueraDeRangoException;
import exceptions.PosicionInvalidaException;
import exceptions.UnidadInvalidaException;
import factory.UnidadFactory;
import factory.unidades.TipoUnidad;

public class CrearNaveTransporteProtoss implements Strategy {
	
	@Override
	public void realizarAccion(ElementoArtificial elementoActuante, Posicion posicionDestino) 
	throws UnidadInvalidaException, FueraDeRangoException, ElementoInvalidoException, PosicionInvalidaException {
		
		UnidadFactory factory = new UnidadFactory();
		
		ElementoArtificial naveTransporte = factory.getUnidad(TipoUnidad.PROTOSS_NAVE_TRANSPORTE, posicionDestino);
		JuegoController.getInstancia().agregarUnidadAJugadorActual(naveTransporte);
		
	}

}

