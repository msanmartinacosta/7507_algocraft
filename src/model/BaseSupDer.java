package model;

import java.io.IOException;

import recursos.Cristal;
import recursos.Volcan;
import strategy.ContextoStrategy;
import titiritero.modelo.ObjetoPosicionable;
import common.Posicion;
import exceptions.CostoInvalidoException;
import exceptions.DanioInvalidoException;
import exceptions.ElementoInvalidoException;
import exceptions.ElementoNoEncontradoException;
import exceptions.EnergiaInsuficienteException;
import exceptions.FactoryInvalidaException;
import exceptions.FinDePartidaException;
import exceptions.FueraDeRangoDeVisionException;
import exceptions.FueraDeRangoException;
import exceptions.PartidaGanadaException;
import exceptions.PartidaPerdidaException;
import exceptions.PoblacionFaltanteException;
import exceptions.PosicionInvalidaException;
import exceptions.RecursosFaltantesException;
import exceptions.RecursosInsuficientesException;
import exceptions.UnidadInvalidaException;
import exceptions.UnidadLlenaException;

public class BaseSupDer extends Base {

	public BaseSupDer(Posicion pos) throws FueraDeRangoException, PosicionInvalidaException, CostoInvalidoException {
		super(pos);		
		int ancho = this.getAnchoCristal();
		int alto = this.getAltoCristal();

		//crea volcan
		Volcan nuevoVolcan = new Volcan(VOLCAN_ALTO, VOLCAN_ANCHO, new Posicion(this.posicion.getX()-ancho, this.posicion.getY()));
		this.volcan.add(nuevoVolcan);
		try {
			Juego.getInstancia().getListener().seCreoVolcan(nuevoVolcan);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//crea cristales
		Cristal cristal = null; 
		cristal = new Cristal(alto, ancho, new Posicion(pos.getX()-(ancho*2), pos.getY()));
		this.cristal.add(cristal);
		try {
			Juego.getInstancia().getListener().seCreoCristal(cristal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cristal = new Cristal(alto, ancho, new Posicion(pos.getX()-(ancho*3), pos.getY()));
		this.cristal.add(cristal);
		try {
			Juego.getInstancia().getListener().seCreoCristal(cristal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cristal = new Cristal(alto, ancho, new Posicion(pos.getX()-(ancho*4), pos.getY()));
		this.cristal.add(cristal);
		try {
			Juego.getInstancia().getListener().seCreoCristal(cristal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cristal = new Cristal(alto, ancho, new Posicion(pos.getX()-ancho, pos.getY()+(ancho)));
		this.cristal.add(cristal);
		try {
			Juego.getInstancia().getListener().seCreoCristal(cristal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cristal = new Cristal(alto, ancho, new Posicion(pos.getX()-ancho, pos.getY()+(ancho*2)));
		this.cristal.add(cristal);
		try {
			Juego.getInstancia().getListener().seCreoCristal(cristal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cristal = new Cristal(alto, ancho, new Posicion(pos.getX()-ancho, pos.getY()+(ancho*3)));
		this.cristal.add(cristal);
		try {
			Juego.getInstancia().getListener().seCreoCristal(cristal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	@Override
	public void posicionar(Posicion nuevaPosicion) throws FueraDeRangoException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void realizarAccion(ContextoStrategy contexto,
			Posicion posicionDestino) throws FactoryInvalidaException,
			UnidadInvalidaException, FueraDeRangoException,
			ElementoInvalidoException, PosicionInvalidaException,
			ElementoNoEncontradoException, FueraDeRangoDeVisionException,
			EnergiaInsuficienteException, CostoInvalidoException,
			RecursosInsuficientesException, CloneNotSupportedException,
			FinDePartidaException, PartidaGanadaException,
			PartidaPerdidaException, UnidadLlenaException,
			RecursosFaltantesException, PoblacionFaltanteException,
			DanioInvalidoException, IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ObjetoPosicionable clonar(Posicion nuevaPosicion)
			throws PosicionInvalidaException, FueraDeRangoException {
		// TODO Auto-generated method stub
		return null;
	}
}

