package strategy;

import model.Elemento;
import model.Juego;

import common.Posicion;

import exceptions.ElementoInvalidoException;
import exceptions.FueraDeRangoException;
import exceptions.PosicionInvalidaException;
import exceptions.UnidadInvalidaException;

public class ObtenerCristal implements Strategy {
	
	public static final int CANTIDAD_DE_CRISTAL=5;
	
	@Override
	public void realizarAccion(Elemento elementoActuante, Posicion posicionDestino) 
	throws UnidadInvalidaException, FueraDeRangoException, ElementoInvalidoException, PosicionInvalidaException {
		
		Juego.getInstancia().getJugadorActual().agregarCantidadDeCristal(CANTIDAD_DE_CRISTAL);
	}

}
