package vista;

import java.io.IOException;

import fiuba.algo3.titiritero.dibujables.Imagen;
import fiuba.algo3.titiritero.modelo.ObjetoPosicionable;

public class VistaMarine extends Imagen {

	public VistaMarine(ObjetoPosicionable modelo) throws IOException {
		super(VistaMarine.class.getResource("/imagenes/marine.png"), modelo);
	}
	
	

}