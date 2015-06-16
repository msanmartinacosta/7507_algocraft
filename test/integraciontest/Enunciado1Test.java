package integraciontest;

import static org.junit.Assert.assertEquals;
import jugador.Jugador;
import jugador.TipoColor;
import model.ElementoArtificial;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import razas.Protoss;
import razas.Terran;
import strategy.ContextoStrategy;
import strategy.Emp;
import common.Posicion;
import controller.JuegoController;
import exceptions.CostoInvalidoException;
import exceptions.DanioInvalidoException;
import exceptions.ElementoInvalidoException;
import exceptions.ElementoNoEncontradoException;
import exceptions.EnergiaInsuficienteException;
import exceptions.FactoryInvalidaException;
import exceptions.FinDePartidaException;
import exceptions.FueraDeRangoDeVisionException;
import exceptions.FueraDeRangoException;
import exceptions.NombreJugadorRepetidoException;
import exceptions.PartidaGanadaException;
import exceptions.PartidaPerdidaException;
import exceptions.PoblacionFaltanteException;
import exceptions.PosicionInvalidaException;
import exceptions.RecursosFaltantesException;
import exceptions.RecursosInsuficientesException;
import exceptions.UnidadInvalidaException;
import exceptions.UnidadLlenaException;
import factory.AbstractFactory;
import factory.GeneradorDeFactory;
import factory.TipoFactory;
import factory.unidades.TipoUnidad;
import factory.unidades.Unidad;

@RunWith(JUnit4.class)
public class Enunciado1Test {

	private Jugador jugadorActual;
	private Jugador jugadorEnemigo;
	AbstractFactory factoryUnidad;
	Unidad naveCiencia;
	Posicion posicionNaveCiencia;
	@Before
	public void setUp() throws Exception {
		
		factoryUnidad=GeneradorDeFactory.getFactory(TipoFactory.UNIDAD_FACTORY);
		posicionNaveCiencia=new Posicion(2,2);
		naveCiencia=factoryUnidad.getUnidad(TipoUnidad.TERRAN_NAVE_CIENCIA,posicionNaveCiencia);
//		naveCiencia.setRangoAtaque("5");//TODO borar despues y arregalr

		jugadorActual = new Jugador("Jugador1",TipoColor.COLOR_ROJO,new Terran());

		jugadorEnemigo = new Jugador("Jugador2",TipoColor.COLOR_AZUL,new Protoss());
		JuegoController.getInstancia().setJugadorActual(jugadorActual);
		JuegoController.getInstancia().setJugadorEnemigo(jugadorEnemigo);
		
	}

	@Test
	public void testNumero1aEnunciado() 
	throws ElementoInvalidoException, RecursosInsuficientesException, NombreJugadorRepetidoException, ElementoNoEncontradoException, FueraDeRangoException, UnidadInvalidaException, CostoInvalidoException, FactoryInvalidaException, PosicionInvalidaException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException {
		
		//Se construye una nave ciencia
		JuegoController.getInstancia().getJugadorActual().agregarElemento(naveCiencia);
		
		//Se pasan turnos hasta que acumule la suficiente energia
		//Suponiendo que es la suficiente energia como para que tire un Emp -> Energia>=100
		//Cada turno carga 10 de energia -> en 5 turnos deberia tener la vida suficiente
		JuegoController.getInstancia().cambiarTurno();//enemigo->60
		assertEquals(60,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//actual->70
		assertEquals(70,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//enemigo->80
		assertEquals(80,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//actual->90
		assertEquals(90,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//enemigo->100 (pero no tiene el control)
		assertEquals(100,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//actual->110
		assertEquals(110,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		//A continuación se situa una unidad protoss
		Posicion posicionZealot=new Posicion(4,4);
		ElementoArtificial zealot = factoryUnidad.getUnidad(TipoUnidad.PROTOSS_ZEALOT, posicionZealot);
		JuegoController.getInstancia().getJugadorEnemigo().agregarElemento(zealot);
		
		
		
		ElementoArtificial naveCienciaObt=JuegoController.getInstancia()
				                                          .getJugadorActual()
				                                          .obtenerArmada()
				                                          .obtenerElementoEnPosicion(posicionNaveCiencia);
		
		//Se aplica Emp al zealot protoss
		ContextoStrategy contexto = new ContextoStrategy(new Emp());
		naveCienciaObt.realizarAccion(contexto, posicionZealot);
		
		//Se verifica que el protoss no tenga mas escudo
		assertEquals(0,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionZealot).getEscudo());
		
		//Se verifica que la nave ciencia tenga menos energia (deberia ser 110-100=10)
		assertEquals(10,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
			
	}
	
	@Test
	public void testNumero1bEnunciado() 
	throws ElementoInvalidoException, RecursosInsuficientesException, ElementoNoEncontradoException, NombreJugadorRepetidoException, UnidadInvalidaException, FueraDeRangoException, CostoInvalidoException, FactoryInvalidaException, PosicionInvalidaException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException {
		
		//Se construye una nave ciencia
		JuegoController.getInstancia().getJugadorActual().agregarElemento(naveCiencia);
		
		//Se pasan turnos hasta que acumule la suficiente energia
		//Suponiendo que es la suficiente energia como para que tire un Emp -> Energia>=100
		//Cada turno carga 10 de energia -> en 5 turnos deberia tener la vida suficiente
		JuegoController.getInstancia().cambiarTurno();//enemigo->60
		assertEquals(60,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//actual->70
		assertEquals(70,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//enemigo->80
		assertEquals(80,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//actual->90
		assertEquals(90,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//enemigo->100 (pero no tiene el control)
		assertEquals(100,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//actual->110
		assertEquals(110,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());

		//Se situa un alto templario y otra nave ciencia, se lanza emp
		Posicion posicionAltoTemplario = new Posicion(5,5);
		ElementoArtificial altoTemplario = factoryUnidad.getUnidad(TipoUnidad.PROTOSS_ALTO_TEMPLARIO, posicionAltoTemplario);
		JuegoController.getInstancia().getJugadorEnemigo().agregarElemento(altoTemplario);
		
		Posicion posicionOtraNaveCiencia = new Posicion(4,4);
		ElementoArtificial otraNaveCiencia = factoryUnidad.getUnidad(TipoUnidad.TERRAN_NAVE_CIENCIA, posicionOtraNaveCiencia);
		JuegoController.getInstancia().getJugadorActual().agregarElemento(otraNaveCiencia);
		
		
		ContextoStrategy contexto = new ContextoStrategy(new Emp());
		
		ElementoArtificial naveCienciaObt=JuegoController.getInstancia()
                .getJugadorActual()
                .obtenerArmada()
                .obtenerElementoEnPosicion(posicionNaveCiencia);
		
		naveCienciaObt.realizarAccion(contexto, posicionAltoTemplario);
		
		//Se verifica que la nave ciencia tenga menos energia
		assertEquals(10,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		//Se verifica que el alto templario y la otra nave ciencia no tengan mas energia
		assertEquals(0,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionAltoTemplario).getEnergia());
		assertEquals(0,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionOtraNaveCiencia).getEnergia());
		
	}
	
	@Test
	public void testNumero1cEnunciado() 
	throws ElementoInvalidoException, RecursosInsuficientesException, NombreJugadorRepetidoException, ElementoNoEncontradoException, FueraDeRangoException, UnidadInvalidaException, CostoInvalidoException, FactoryInvalidaException, PosicionInvalidaException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException {
		
		//Se construye una nave ciencia
		JuegoController.getInstancia().getJugadorActual().agregarElemento(naveCiencia);
		
		//Se pasan turnos hasta que acumule la suficiente energia
		//Suponiendo que es la suficiente energia como para que tire un Emp -> Energia>=100
		//Cada turno carga 10 de energia -> en 5 turnos deberia tener la vida suficiente
		JuegoController.getInstancia().cambiarTurno();//enemigo->60
		assertEquals(60,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//actual->70
		assertEquals(70,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//enemigo->80
		assertEquals(80,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//actual->90
		assertEquals(90,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//enemigo->100 (pero no tiene el control)
		assertEquals(100,JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		JuegoController.getInstancia().cambiarTurno();//actual->110
		assertEquals(110,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());

		//Se situa un alto templario y otra nave ciencia, se lanza emp
		Posicion posicionAltoTemplario = new Posicion(12,12);
		ElementoArtificial altoTemplario = factoryUnidad.getUnidad(TipoUnidad.PROTOSS_ALTO_TEMPLARIO, posicionAltoTemplario);
		JuegoController.getInstancia().getJugadorEnemigo().agregarElemento(altoTemplario);
		
		//A continuación se situa una unidad protoss
		Posicion posicionZealot=new Posicion(15,15);
		ElementoArtificial zealot = factoryUnidad.getUnidad(TipoUnidad.PROTOSS_ZEALOT, posicionZealot);
		JuegoController.getInstancia().getJugadorEnemigo().agregarElemento(zealot);
		
		
		ContextoStrategy contexto = new ContextoStrategy(new Emp());
		
		ElementoArtificial naveCienciaObt=JuegoController.getInstancia()
                .getJugadorActual()
                .obtenerArmada()
                .obtenerElementoEnPosicion(posicionNaveCiencia);
		
		naveCienciaObt.realizarAccion(contexto, new Posicion(3,3));
		
		//Se verifica que la nave ciencia tenga menos energia
		assertEquals(10,JuegoController.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionNaveCiencia).getEnergia());
		
		//Se verifica que el alto templario y la unidad protoss no hayan sufrido modificaciones
		//Escudo Zealot = 60, vida Zealot = 100, Energia AltoTemplario = 50 (ya que es magica, le sacaria energia)
		ElementoArtificial zealotRes=JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionZealot);
		assertEquals(60,zealotRes.getEscudo());
		assertEquals(100,zealotRes.getVida());
		
		ElementoArtificial altoTemplarioRes=JuegoController.getInstancia().getJugadorEnemigo().obtenerArmada().obtenerElementoEnPosicion(posicionAltoTemplario);
		assertEquals(50,altoTemplarioRes.getEnergia());
		
	}

}
