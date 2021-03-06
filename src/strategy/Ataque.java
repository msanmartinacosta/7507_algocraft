package strategy;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import model.CampoBatalla;
import model.Elemento;
import model.ElementoArtificial;
import model.Espacio;
import model.Juego;
import common.Mensajes;
import common.Posicion;
import exceptions.ElementoNoEncontradoException;
import exceptions.FactoryInvalidaException;
import exceptions.FinDePartidaException;
import exceptions.FueraDeRangoDeVisionException;
import exceptions.FueraDeRangoException;
import exceptions.PartidaGanadaException;
import exceptions.PartidaPerdidaException;
import exceptions.PosicionInvalidaException;
import factory.unidades.Unidad;

public class Ataque implements Strategy {

	@Override
	public void realizarAccion(Elemento elementoActuante, Posicion posicionDestino) 
	throws FactoryInvalidaException, ElementoNoEncontradoException, FueraDeRangoDeVisionException, FinDePartidaException, PartidaGanadaException, PartidaPerdidaException, PosicionInvalidaException, FueraDeRangoException {
				
		int factor = elementoActuante.getAncho();
		int distancia = posicionDestino.getDistancia(elementoActuante.getPosicion());
		
		int rangoDeVisionElementoActuante=((Unidad)elementoActuante).getVision();
		
		if(distancia>rangoDeVisionElementoActuante*factor)
			throw new FueraDeRangoDeVisionException(Mensajes.MSJ_ERROR_FUERA_DE_RANGO_DE_VISION);
	
		List<Elemento> elementosAtacablesAereos=CampoBatalla.getInstancia().obtenerElementosAereos();
		List<Elemento> elementosAtacablesTerrestres=CampoBatalla.getInstancia().obtenerElementosTerrestres();
		
		elementosAtacablesAereos.addAll(elementosAtacablesTerrestres);
		
		List<Elemento> elementosAtacados=new ArrayList<Elemento>(elementosAtacablesAereos);
		
		
		ListIterator<Elemento> it = elementosAtacados.listIterator();
		Espacio espacioUnidad = elementoActuante.obtenerEspacio();
		
		while(it.hasNext()) {
			
			//Obtengo la unidad atacada
			Elemento elementoAtacado = it.next();

			//Me fijo que la posici�n de destino sea parte de la unidad atacada
			if(elementoAtacado.posicionEsParte(posicionDestino)) 
			{
				Espacio espacioTemporal = ((ElementoArtificial) elementoAtacado).obtenerEspacio();
				
				int danioAtaqueNum=espacioTemporal.getDanio(((Unidad) elementoActuante).getDanio());
				int rangoAtaqueAtacante = espacioUnidad.getRangoDeAtaque(((Unidad) elementoActuante).getRangoAtaque());

				if(distancia<=rangoAtaqueAtacante*factor)
				{
					elementoAtacado.restarVitalidad(danioAtaqueNum);
					it.set(elementoAtacado);
					if (elementoAtacado.estaMuerta()) {
						Juego.getInstancia().getListener().seMurioUnaUnidad(elementoAtacado);
					}
					Juego.getInstancia().getListener().seRealizoAtaque((ElementoArtificial)elementoAtacado);
					Juego.getInstancia().verificarFinDePartida();
					return;
				}
				else
				{
					throw new FueraDeRangoException("La unidad est� fuera del rango de ataque.");
				}
			}
		}
		
		throw new PosicionInvalidaException("La posici�n est� vac�a. No hay unidades enemigas para atacar");
	}			
}