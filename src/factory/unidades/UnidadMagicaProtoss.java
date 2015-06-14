package factory.unidades;

import model.Espacio;

import command.AlucinacionAccion;
import command.TormentaPsionicaAccion;
import common.Costo;
import common.Posicion;
import common.Vitalidad;

import exceptions.FueraDeRangoException;

public class UnidadMagicaProtoss extends UnidadMagica {
	
	private static final int ENERGIA_MAXIMA=200;
	private static final int ENERGIA_CAMBIO_DE_TURNO=15;
	private static final int ESCUDO_CAMBIO_DE_TURNO=10;
	
	public UnidadMagicaProtoss(int unidadTransporte,
			int unidadVision, Costo unidadCosto,
			int unidadTiempoConstruccion,
			String unidadDa�o, int unidadSuministro,
			String unidadRangoAtaque,
			Vitalidad unidadVida, int unidadAlto, int unidadAncho,
			Posicion posicion, Espacio espacio) throws FueraDeRangoException {
		
		super(unidadTransporte,
			unidadVision, unidadCosto,
			unidadTiempoConstruccion,
			unidadDa�o,unidadSuministro,
			unidadRangoAtaque,
			unidadVida,unidadAlto, unidadAncho,
			posicion, espacio);
		
		this.definirAccionesDisponibles();
	}

	public UnidadMagicaProtoss() {
		// TODO Auto-generated constructor stub
	}

	public void definirAccionesDisponibles(){
		//super.definirAccionesDisponibles();
		
		//TODO msma: Agregar validacion de vida/Energia cuando este implementado
		agregarAccionDisponible("TormentaPsionica", new TormentaPsionicaAccion(this));
		agregarAccionDisponible("Alucinacion",new AlucinacionAccion(this));
	}
	
	@Override
	public void agregarEnergiaPorPasoDeTurno(){
		
		if(this.getEnergia()>=ENERGIA_MAXIMA-ENERGIA_CAMBIO_DE_TURNO)
			this.setEnergia(ENERGIA_MAXIMA);
		else
			this.setEnergia(this.getEnergia()+ENERGIA_CAMBIO_DE_TURNO);
	}
	
	@Override
	public void agregarEscudoPorPasoDeTurno() {
		setEscudo(getEscudo()+ESCUDO_CAMBIO_DE_TURNO);
		
	}

}