package strategytest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import jugador.Jugador;
import model.Armada;
import model.CampoBatalla;
import model.Elemento;
import model.Juego;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import strategy.ContextoStrategy;
import strategy.CrearAltoTemplario;
import strategy.CrearEspectro;
import strategy.CrearGolliat;
import strategy.CrearMarine;
import strategy.CrearNaveCiencia;
import strategy.CrearNaveTransporteProtoss;
import strategy.CrearNaveTransporteTerran;
import strategy.CrearScout;
import strategy.CrearZealot;
import vista.VentanaMock;

import common.Costo;
import common.Danio;
import common.Posicion;
import common.RangoAtaque;
import common.Vitalidad;

import exceptions.ColorInvalidoException;
import exceptions.CostoInvalidoException;
import exceptions.DanioInvalidoException;
import exceptions.ElementoInvalidoException;
import exceptions.ElementoNoEncontradoException;
import exceptions.EnergiaInsuficienteException;
import exceptions.FactoryInvalidaException;
import exceptions.FinDePartidaException;
import exceptions.FueraDeRangoDeVisionException;
import exceptions.FueraDeRangoException;
import exceptions.NombreCortoException;
import exceptions.PartidaGanadaException;
import exceptions.PartidaPerdidaException;
import exceptions.PoblacionFaltanteException;
import exceptions.PosicionInvalidaException;
import exceptions.RecursosFaltantesException;
import exceptions.RecursosInsuficientesException;
import exceptions.UnidadInvalidaException;
import exceptions.UnidadLlenaException;
import factory.construcciones.Acceso;
import factory.construcciones.ArchivoTemplario;
import factory.construcciones.Barraca;
import factory.construcciones.Edificio;
import factory.construcciones.Fabrica;
import factory.construcciones.PuertoEstelarProtoss;
import factory.construcciones.PuertoEstelarTerran;
import factory.unidades.Unidad;

public class CrearUnidadesTest {
	
	Jugador jugadorActualTerran;
	Jugador jugadorActualProtoss;
	ContextoStrategy contexto;
	private Armada armada;
	Posicion posicionOrigen;
	Posicion posicionDestino;
	
	@Before
	public void setUp() throws NombreCortoException, ColorInvalidoException, FueraDeRangoException, PosicionInvalidaException {
		
		Juego.crearInstancia(new VentanaMock());
		posicionOrigen=new Posicion(10,10);
		posicionDestino = new Posicion(150,15);
		jugadorActualTerran = new Jugador("jugador1","terran","rojo");
		jugadorActualProtoss = new Jugador("jugador2","protoss","azul");
		jugadorActualTerran.agregarCantidadDeCristal(2000);
		jugadorActualTerran.agregarCantidadDeGas(2000);
		jugadorActualTerran.setPoblacionDisponible(200000);
		jugadorActualProtoss.setGas(20000);

		Juego.getInstancia().setJugadorActual(jugadorActualTerran);

	}
	
	@After
	public void destroy(){
		CampoBatalla.DestruirInstancia();
		Juego.destruirInstancia();
	}
	
	/*
	 * Jugador pide a barraca crear marine
	 */
	@Test
	public void testJugadorConBarracaCreaMarine() 
	throws ElementoNoEncontradoException, FactoryInvalidaException, UnidadInvalidaException, FueraDeRangoException, ElementoInvalidoException, PosicionInvalidaException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CostoInvalidoException, RecursosInsuficientesException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException, IOException {
		Edificio barraca = new Barraca(2, 2, posicionOrigen);
		Juego.getInstancia().agregarUnidadAJugadorActual(barraca);
		contexto = new ContextoStrategy(new CrearMarine());
		armada = Juego.getInstancia().getJugadorActual().obtenerArmada();
		Edificio barracaObtenida = (Edificio) armada.obtenerElementoEnPosicion(posicionOrigen);
		barracaObtenida.realizarAccion(contexto, posicionDestino);
		Unidad unidad = (Unidad) Juego.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionDestino);
		assertEquals(1,unidad.getTransporte());
		assertEquals(7, unidad.getVision());
		assertEquals(true, unidad.getCosto().equals(new Costo("50M")));
		assertEquals(3, unidad.getTiempoConstruccion());	
		assertEquals(true, new Danio("6A6T").equals(unidad.getDanio()));
		assertEquals(true, new RangoAtaque(0,4).equals(unidad.getRangoAtaque()));
		assertEquals(40, unidad.getVida());	

	}
	
	/*
	 * Jugador pide a Acceso crear Zealot
	 */
	@Test
	public void testJugadorConAccesoCreaZealot() 
	throws FueraDeRangoException, ElementoInvalidoException, PosicionInvalidaException, ElementoNoEncontradoException, FactoryInvalidaException, UnidadInvalidaException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CostoInvalidoException, RecursosInsuficientesException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException, IOException {

		Edificio edificio = new Acceso(2, 2, posicionOrigen);
		Juego.getInstancia().agregarUnidadAJugadorActual(edificio);
		contexto = new ContextoStrategy(new CrearZealot());
		armada = Juego.getInstancia().getJugadorActual().obtenerArmada();
		Elemento accesoObtenido = armada.obtenerElementoEnPosicion(posicionOrigen);
		accesoObtenido.realizarAccion(contexto, posicionDestino);
		Unidad unidad = (Unidad) Juego.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionDestino);
		assertEquals(2,unidad.getTransporte());
		assertEquals(7, unidad.getVision());
		assertEquals(true, unidad.getCosto().equals(new Costo("100M")));
		assertEquals(4, unidad.getTiempoConstruccion());	
		assertEquals(true, new Danio("0A8T").equals(unidad.getDanio()));
		assertEquals(true, new RangoAtaque(0,1).equals(unidad.getRangoAtaque()));
		assertEquals(true, unidad.getVitalidad().equals(new Vitalidad(100,60)));
	}
	
	/*
	 * Jugador pide Archivo templario crea alto templario
	 */
	@Test
	public void testJugadorConArchivoTemplarioCreaAltoTemplario() 
	throws ElementoInvalidoException, PosicionInvalidaException, ElementoNoEncontradoException, FueraDeRangoException, FactoryInvalidaException, UnidadInvalidaException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CostoInvalidoException, RecursosInsuficientesException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException, IOException {
		Edificio acceso = new ArchivoTemplario(2, 2, posicionOrigen);
		Juego.getInstancia().agregarUnidadAJugadorActual(acceso);
		contexto = new ContextoStrategy(new CrearAltoTemplario());
		armada = Juego.getInstancia().getJugadorActual().obtenerArmada();
		Edificio accesoObtenido = (Edificio) armada.obtenerElementoEnPosicion(posicionOrigen);
		accesoObtenido.realizarAccion(contexto, posicionDestino);
		Unidad unidad = (Unidad)Juego.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionDestino);
		assertEquals(2,unidad.getTransporte());
		assertEquals(7, unidad.getVision());
		assertEquals(true, unidad.getCosto().equals(new Costo("50M150G")));
		assertEquals(7, unidad.getTiempoConstruccion());	
		assertEquals(true, new Danio("0A0T").equals(unidad.getDanio()));
		assertEquals(true, new RangoAtaque(0,0).equals(unidad.getRangoAtaque()));
		assertEquals(true, unidad.getVitalidad().equals(new Vitalidad(40,40)));	
		
		
	}
	

	
	/*
	 * Jugador pide a fabrica crear golliat
	 */
	@Test
	public void testJugadorConFabricaCreaGolliat() 
	throws FactoryInvalidaException, UnidadInvalidaException, FueraDeRangoException, ElementoInvalidoException, PosicionInvalidaException, ElementoNoEncontradoException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CostoInvalidoException, RecursosInsuficientesException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException, IOException {
		Edificio fabrica = new Fabrica(2, 2, posicionOrigen);
		Juego.getInstancia().agregarUnidadAJugadorActual(fabrica);
		contexto = new ContextoStrategy(new CrearGolliat());
		armada = Juego.getInstancia().getJugadorActual().obtenerArmada();
		Edificio fabricaObtenida = (Edificio) armada.obtenerElementoEnPosicion(posicionOrigen);
		fabricaObtenida.realizarAccion(contexto, posicionDestino);
		Unidad unidad = (Unidad) Juego.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionDestino);
		assertEquals(2,unidad.getTransporte());
		assertEquals(8, unidad.getVision());
		assertEquals(true, unidad.getCosto().equals(new Costo("100M50G")));
		assertEquals(6, unidad.getTiempoConstruccion());	
		assertEquals(true, new Danio("10A12T").equals(unidad.getDanio()));
		assertEquals(true, new RangoAtaque(5,6).equals(unidad.getRangoAtaque()));
		assertEquals(125, unidad.getVida());	
	}

	
	/*
	 * Jugador pide a puerto estelar protoss crear scout
	 */
	@Test
	public void testJugadorConPuertoEstelarProtossCreaScout() throws FueraDeRangoException, FactoryInvalidaException, UnidadInvalidaException, ElementoInvalidoException, PosicionInvalidaException, ElementoNoEncontradoException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CostoInvalidoException, RecursosInsuficientesException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException, IOException {
		Edificio puertoEstelarProtoss = new PuertoEstelarProtoss(2, 2, posicionOrigen);
		Juego.getInstancia().agregarUnidadAJugadorActual(puertoEstelarProtoss);
		contexto = new ContextoStrategy(new CrearScout());
		armada = Juego.getInstancia().getJugadorActual().obtenerArmada();
		Edificio puertoEstelarProtossObtenido = (Edificio) armada.obtenerElementoEnPosicion(posicionOrigen);
		puertoEstelarProtossObtenido.realizarAccion(contexto, posicionDestino);
		Unidad unidad = (Unidad) Juego.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionDestino);
		assertEquals(0,unidad.getTransporte());
		assertEquals(7, unidad.getVision());
		assertEquals(true, unidad.getCosto().equals(new Costo("300M150G")));
		assertEquals(9, unidad.getTiempoConstruccion());	
		assertEquals(true, new Danio("14A8T").equals(unidad.getDanio()));
		assertEquals(true, new RangoAtaque(4,0).equals(unidad.getRangoAtaque()));
		assertEquals(true, unidad.getVitalidad().equals(new Vitalidad(150,100)));	

		
	}
	
	/*
	 * Jugador pide a puerto estelar protoss crear nave de transporte
	 */
	@Test
	public void testJugadorConPuertoEstelarProtossCreaNaveTransporteProtoss() throws ElementoNoEncontradoException, ElementoInvalidoException, PosicionInvalidaException, FueraDeRangoException, FactoryInvalidaException, UnidadInvalidaException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CostoInvalidoException, RecursosInsuficientesException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException, IOException {
		Edificio puertoEstelarProtoss = new PuertoEstelarProtoss(2, 2, posicionOrigen);
		Juego.getInstancia().agregarUnidadAJugadorActual(puertoEstelarProtoss);
		contexto = new ContextoStrategy(new CrearNaveTransporteProtoss());
		armada = Juego.getInstancia().getJugadorActual().obtenerArmada();
		Edificio puertoEstelarProtossObtenido = (Edificio) armada.obtenerElementoEnPosicion(posicionOrigen);
		puertoEstelarProtossObtenido.realizarAccion(contexto, posicionDestino);
		Unidad unidad = (Unidad) Juego.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionDestino);
		assertEquals(8,unidad.getTransporte());
		assertEquals(8, unidad.getVision());
		assertEquals(true, unidad.getCosto().equals(new Costo("200M")));
		assertEquals(8, unidad.getTiempoConstruccion());	
		assertEquals(true, new Danio("0A0T").equals(unidad.getDanio()));
		assertEquals(true, new RangoAtaque(0,0).equals(unidad.getRangoAtaque()));
		assertEquals(true, unidad.getVitalidad().equals(new Vitalidad(80,60)));	
		
	}
	
	/*
	 * Jugador pide a puerto estelar terran crear espectro
	 */
	@Test
	public void testJugadorConPuertoEstelarTerranCreaEspectro() throws FueraDeRangoException, ElementoNoEncontradoException, FactoryInvalidaException, UnidadInvalidaException, ElementoInvalidoException, PosicionInvalidaException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CostoInvalidoException, RecursosInsuficientesException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException, IOException {
		Edificio puertoEstelarTerran = new PuertoEstelarTerran(2, 2, posicionOrigen);
		Juego.getInstancia().agregarUnidadAJugadorActual(puertoEstelarTerran);
		contexto = new ContextoStrategy(new CrearEspectro());
		armada = Juego.getInstancia().getJugadorActual().obtenerArmada();
		Edificio puertoEstelarTerranObtenido = (Edificio) armada.obtenerElementoEnPosicion(posicionOrigen);
		puertoEstelarTerranObtenido.realizarAccion(contexto, posicionDestino);
		Unidad unidad = (Unidad) Juego.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionDestino);
		assertEquals(0,unidad.getTransporte());
		assertEquals(7, unidad.getVision());
		assertEquals(true, unidad.getCosto().equals(new Costo("150M100G")));
		assertEquals(8, unidad.getTiempoConstruccion());	
		assertEquals(true, new Danio("20A8T").equals(unidad.getDanio()));
		assertEquals(true, new RangoAtaque(5,0).equals(unidad.getRangoAtaque()));
		assertEquals(120, unidad.getVida());	
		
	}
	
	/*
	 * Jugador pide a puerto estelar terran crear nave de transporte
	 */
	@Test
	public void testJugadorConPuertoEstelarTerranCreaNaveTransporteTerran() throws FactoryInvalidaException, UnidadInvalidaException, FueraDeRangoException, ElementoInvalidoException, PosicionInvalidaException, ElementoNoEncontradoException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CostoInvalidoException, RecursosInsuficientesException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException, IOException {
		Edificio puertoEstelarTerran = new PuertoEstelarTerran(2, 2, posicionOrigen);
		Juego.getInstancia().agregarUnidadAJugadorActual(puertoEstelarTerran);
		contexto = new ContextoStrategy(new CrearNaveTransporteTerran());
		armada = Juego.getInstancia().getJugadorActual().obtenerArmada();
		Edificio puertoEstelarTerranObtenido = (Edificio) armada.obtenerElementoEnPosicion(posicionOrigen);
		puertoEstelarTerranObtenido.realizarAccion(contexto, posicionDestino);
		Unidad unidad = (Unidad) Juego.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionDestino);
		assertEquals(8,unidad.getTransporte());
		assertEquals(8, unidad.getVision());
		assertEquals(true, unidad.getCosto().equals(new Costo("100M100G")));
		assertEquals(7, unidad.getTiempoConstruccion());	
		assertEquals(true, new Danio("0A0T").equals(unidad.getDanio()));
		assertEquals(true, new RangoAtaque(0,0).equals(unidad.getRangoAtaque()));
		assertEquals(150, unidad.getVida());	
	}
	
	@Test
	public void testJugadorConPuertoEstelarTerranCreaNaveCiencia() throws ElementoNoEncontradoException, FactoryInvalidaException, UnidadInvalidaException, FueraDeRangoException, ElementoInvalidoException, PosicionInvalidaException, FueraDeRangoDeVisionException, EnergiaInsuficienteException, CostoInvalidoException, RecursosInsuficientesException, CloneNotSupportedException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, UnidadLlenaException, RecursosFaltantesException, PoblacionFaltanteException, DanioInvalidoException, IOException {
		Edificio puertoEstelarTerran = new PuertoEstelarTerran(2, 2, posicionOrigen);
		Juego.getInstancia().agregarUnidadAJugadorActual(puertoEstelarTerran);
		contexto = new ContextoStrategy(new CrearNaveCiencia());
		armada = Juego.getInstancia().getJugadorActual().obtenerArmada();
		Edificio puertoEstelarTerranObtenido = (Edificio) armada.obtenerElementoEnPosicion(posicionOrigen);
		puertoEstelarTerranObtenido.realizarAccion(contexto, posicionDestino);
		Unidad unidad = (Unidad) Juego.getInstancia().getJugadorActual().obtenerArmada().obtenerElementoEnPosicion(posicionDestino);
		assertEquals(0,unidad.getTransporte());
		assertEquals(10, unidad.getVision());
		assertEquals(true, unidad.getCosto().equals(new Costo("100M225G")));
		assertEquals(10, unidad.getTiempoConstruccion());	
		assertEquals(true, new Danio("0A0T").equals(unidad.getDanio()));
		assertEquals(true, new RangoAtaque(0,0).equals(unidad.getRangoAtaque()));
		assertEquals(200, unidad.getVida());	

		
	}

}
