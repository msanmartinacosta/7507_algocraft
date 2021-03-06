package vista;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import listener.JuegoListener;
import model.Elemento;
import model.ElementoArtificial;
import model.Juego;
import razas.Raza;
import recursos.Cristal;
import sonido.Reproductor;
import sonido.TipoSonido;
import titiritero.dibujables.Imagen;
import titiritero.dibujables.SuperficiePanel;
import titiritero.modelo.GameLoop;
import titiritero.modelo.ObjetoDibujable;
import titiritero.modelo.SuperficieDeDibujo;
import turno.TimerCambioDeTurno;
import vista.edificios.VistaAcceso;
import vista.edificios.VistaArchivosTemplarios;
import vista.edificios.VistaAsimilador;
import vista.edificios.VistaBarraca;
import vista.edificios.VistaCentroComandoProtoss;
import vista.edificios.VistaCentroComandoTerran;
import vista.edificios.VistaCentroMineral;
import vista.edificios.VistaCristal;
import vista.edificios.VistaDepositoDeSuministros;
import vista.edificios.VistaFabrica;
import vista.edificios.VistaGas;
import vista.edificios.VistaNexoMineral;
import vista.edificios.VistaPilon;
import vista.edificios.VistaPuertoEstelarProtoss;
import vista.edificios.VistaPuertoEstelarTerran;
import vista.edificios.VistaRefineria;
import vista.unidades.VistaAltoTemplario;
import vista.unidades.VistaDragon;
import vista.unidades.VistaEspectro;
import vista.unidades.VistaGolliat;
import vista.unidades.VistaMarine;
import vista.unidades.VistaNaveCiencia;
import vista.unidades.VistaNaveTransporteProtoss;
import vista.unidades.VistaNaveTransporteTerran;
import vista.unidades.VistaScout;
import vista.unidades.VistaZealot;

import command.Accion;

import controller.ControladorCampoBatalla;
import controller.ControladorMouse;
import exceptions.ElementoNoEncontradoException;
import exceptions.FueraDeRangoException;
import exceptions.PosicionInvalidaException;


public class VentanaPrincipal implements JuegoListener {

	private static JFrame frame;
	private GameLoop gameLoop;
	private Juego juego;
	private ArrayList<JButton> botonesAccion;
	private static JLabel labelNombre;
	private static JLabel labelGas;
	private static JLabel labelCristal;
	private static JLabel labelRaza;
	private static JLabel labelPoblacion;
	private ControladorCampoBatalla controladorCampoBatalla;
	private JLabel labelInfoUnidad;

	/**
	 * Create the application.
	 * @throws PosicionInvalidaException 
	 * @throws FueraDeRangoException 
	 */
	public VentanaPrincipal() throws FueraDeRangoException, PosicionInvalidaException {
		try {
			initialize();
			this.gameLoop.agregarObservador(controladorCampoBatalla);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws PosicionInvalidaException 
	 * @throws FueraDeRangoException 
	 */
	private void initialize() throws IOException, FueraDeRangoException, PosicionInvalidaException {
		//ventana principal
		setFrame(new JFrame("Algocraft"));
		this.botonesAccion = new ArrayList<JButton>();
		labelGas=new JLabel("Gas");
		labelCristal=new JLabel("Cristal");
		labelNombre= new JLabel("Nombre");
		labelRaza = new JLabel("Raza");
		labelInfoUnidad = new JLabel("InfoUnidad");
		labelPoblacion = new JLabel("Poblacion");
		getFrame().setExtendedState(getFrame().getExtendedState() | JFrame.MAXIMIZED_BOTH);
		getFrame().setForeground(new Color(0,0,0));
		getFrame().setBounds(1, 1, 1366, 768);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		frame.setVisible(true);
		
		controladorCampoBatalla = new ControladorCampoBatalla(this);

		
		//boton iniciar juego
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameLoop.iniciarEjecucion();
				Reproductor.getInstancia().loopSonido(TipoSonido.MUSICA);
				agregarInformacionDeJugador();
				agregarBotonCambioDeTurno();
				
			}
		});
		btnIniciar.setBounds(25, 25, 100, 25);
		getFrame().getContentPane().add(btnIniciar);
	
		//boton finalizar juego
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameLoop.detenerEjecucion();
				
			}
		});
		btnFinalizar.setBounds(135, 25, 100, 25);
		getFrame().getContentPane().add(btnFinalizar);
		
		//boton cambio de turno


		//superficie donde se dibujarn los elementos
		JPanel panel = new SuperficiePanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(25, 60, 1000, 600);
//		panel.setBounds(25, 60, 1000, 658);
		getFrame().getContentPane().add(panel);
		
		this.gameLoop = new GameLoop((SuperficieDeDibujo) panel);
	
		getFrame().setFocusable(true);
		btnIniciar.setFocusable(false);
		btnFinalizar.setFocusable(false);
			
		panel.addMouseListener(new ControladorMouse(this));
		//Se reproduce la musica
//		Reproductor.getInstancia().loopSonido(TipoSonido.MUSICA);
		
//		Timer tiempoDeTurno = new Timer();
//		TimerCambioDeTurno cambioDeTurno= new TimerCambioDeTurno();
//		tiempoDeTurno.schedule(cambioDeTurno,10000, 10000);
		
	}
	
	protected void agregarBotonCambioDeTurno() {
		
		JButton btnCambioDeTurno = new JButton("Pasar turno");
		btnCambioDeTurno.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TimerCambioDeTurno cambioDeTurno= new TimerCambioDeTurno();
				
				cambioDeTurno.run();
			
				//tiempoDeTurno.schedule(cambioDeTurno,12000, 120000);
				
			}
		});
		btnCambioDeTurno.setBounds(1125, 500, 125, 25);
		getFrame().getContentPane().add(btnCambioDeTurno);
		btnCambioDeTurno.repaint();
		
	}
	
	public void agregarInformacionDeUnidad(Elemento elemento) {
		labelInfoUnidad.setText("");
		
		labelInfoUnidad.setBounds(1125, 300, 200, 300);
		labelInfoUnidad.setText(elemento.toString());
		frame.getContentPane().add(labelInfoUnidad);
		labelInfoUnidad.repaint();
		
	}

	public void agregarPanelDeOpciones(Map<String, Accion> opciones){
		Set<String> keys = opciones.keySet();
		int posX = 1035;
		int posY = 60;
		
		Iterator<String> it = keys.iterator();
		while (it.hasNext()){
			String keyActual = it.next();
			JButton botonAccion = new JButton(keyActual);
			Accion accionActual = opciones.get(keyActual);
			botonAccion.addActionListener(new CreadorBotonDinamico(accionActual));
			botonAccion.setBounds(posX, posY, 250, 25);
			getFrame().getContentPane().add(botonAccion);
			botonAccion.repaint();
			botonAccion.setFocusable(true);
			this.botonesAccion.add(botonAccion);
			posY += 25;
		}
	}
	
	public static void agregarInformacionDeJugador() {
		
		//Información del nombre
		labelNombre.setText("");
		//labelNombre = new JLabel("Nombre");
		labelNombre.setBounds(300, 25, 100, 25);
		labelNombre.setText("Nombre: " + Juego.getInstancia().getJugadorActual().getNombre());
		frame.getContentPane().add(labelNombre);
		labelNombre.repaint();
		
		//Informacion de la raza
		labelRaza.setText("");
		labelRaza.setBounds(450,25,100,25);
		labelRaza.setText("Raza: " + Juego.getInstancia().getJugadorActual().getRaza().toString());
		frame.getContentPane().add(labelRaza);
		labelRaza.repaint();
		
		//Información de cristal
		labelCristal.setText("");
		//labelCristal = new JLabel("Cristal");
		labelCristal.setBounds(600, 25, 125, 25);
		labelCristal.setText("Cristales: " + String.valueOf(Juego.getInstancia().getJugadorActual().getCantidadDeCristal()));
		frame.getContentPane().add(labelCristal);
		labelCristal.repaint();
		
		//Información de gas
		labelGas.setText("");
		//labelGas = new JLabel("Gas");
		labelGas.setBounds(750, 25, 100, 25);
		labelGas.setText("Gas: " + String.valueOf(Juego.getInstancia().getJugadorActual().getCantidadDeGas()));
		frame.getContentPane().add(labelGas);
		labelGas.repaint();
		
		//Información de poblacion actual
		labelPoblacion.setText("");
		//labelGas = new JLabel("Gas");
		labelPoblacion.setBounds(850, 25, 150, 25);
		labelPoblacion.setText("Poblacion: " + String.valueOf(Juego.getInstancia().getJugadorActual().getPoblacionActual()+"/"+ String.valueOf(Juego.getInstancia().getJugadorActual().getPoblacionDisponible())));
		frame.getContentPane().add(labelPoblacion);
		labelPoblacion.repaint();
		
	}
	
	public void limpiarPanelDeOpciones(){
		for (JButton jButton : botonesAccion) {
			jButton.setVisible(false);
		}		
	}
	public Juego getJuego(){
		return this.juego;
	}
		
	public GameLoop getGameLoop() {
		return gameLoop;
	}

	public void setGameLoop(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
	}
	@Override
	public String pruebaListener() {
		return "pruebaListener()";
	}

	@Override
	public void seCreoMarine(ElementoArtificial elemento) throws IOException, FueraDeRangoException, PosicionInvalidaException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaMarine(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoGolliat(ElementoArtificial elemento) throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaGolliat(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
		
	}

	@Override
	public void seCreoEspectro(ElementoArtificial elemento) throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaEspectro(elemento);
		this.getGameLoop().agregar(imagen);
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
		
		
	}

	@Override
	public void seCreoNaveCiencia(ElementoArtificial elemento) throws IOException, FueraDeRangoException, PosicionInvalidaException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaNaveCiencia(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
		
	}

	@Override
	public void seCreoNaveTransporteTerran(ElementoArtificial elemento) throws IOException, FueraDeRangoException, PosicionInvalidaException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaNaveTransporteTerran(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoZealot(ElementoArtificial elemento) throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaZealot(elemento);
		this.getGameLoop().agregar(imagen);
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	
	}

	@Override
	public void seCreoDragon(ElementoArtificial elemento)  throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaDragon(elemento);
		this.getGameLoop().agregar(imagen);
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
		
	}

	@Override
	public void seCreoScout(ElementoArtificial elemento)  throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaScout(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoAltoTemplario(ElementoArtificial elemento)  throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaAltoTemplario(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoNaveTransporteProtoss(ElementoArtificial elemento)  throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaNaveTransporteProtoss(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoCentroMineral(ElementoArtificial elemento) throws FueraDeRangoException, PosicionInvalidaException, IOException  {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaCentroMineral(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}
	

	@Override
	public void seCreoBarraca(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaBarraca(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoDepositoDeSuministro(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaDepositoDeSuministros(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoRefineria(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaRefineria(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoFabrica(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaFabrica(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoPuertoEstelarTerran(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaPuertoEstelarTerran(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoNexoMineral(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaNexoMineral(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoPilon(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaPilon(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoAsimilador(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaAsimilador(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoAcceso(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaAcceso(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoPuertoEstelarProtoss(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaPuertoEstelarProtoss(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seCreoArchivosTemplarios(ElementoArtificial elemento)throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaArchivosTemplarios(elemento);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(elemento, imagen);
	}

	@Override
	public void seMovioUnidad(ElementoArtificial elemento) {
		//this.getGameLoop().agregar(elemento);
		//Imagen imagen = new VistaArchivosTemplarios(elemento);
		//this.getGameLoop().agregar(imagen);	
		
	}

	@Override
	public void seRealizoAtaque(Elemento elemento) {
		
		JOptionPane.showMessageDialog(getFrame(), "Ataque sobre unidad enemiga realizado exitosamente", "Ataque Exitoso",JOptionPane.INFORMATION_MESSAGE);
		
		
	}

	@Override
	public void seRealizoEmp() {
		JOptionPane.showMessageDialog(getFrame(), "Emp sobre unidad enemiga realizado exitosamente", "Ataque Exitoso",JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void seRealizoRadiacion(Elemento elemento) {
		JOptionPane.showMessageDialog(getFrame(), "Radiacion sobre unidad enemiga realizada exitosamente", "Ataque Exitoso",JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void seRealizoTormentaPsionica(Elemento elemento) {
		JOptionPane.showMessageDialog(getFrame(), "Tormenta Psiónica sobre unidad enemiga realizada exitosamente", "Ataque Exitoso",JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void seRealizoAlucinacion(Elemento elemento, Elemento copia1, Elemento copia2) throws PosicionInvalidaException, FueraDeRangoException {
		
		this.getGameLoop().agregar(copia1);
		this.getGameLoop().agregar(copia2);
		
		ObjetoDibujable original = ControladorCampoBatalla.obtenerVista(elemento);
		
		ObjetoDibujable copiaDib1=original.clonar(copia1.getPosicion());
		ObjetoDibujable copiaDib2=original.clonar(copia2.getPosicion());
		
		copia1 = (Elemento) ((Imagen)copiaDib1).getPosicionable();
		copia2 = (Elemento) ((Imagen)copiaDib2).getPosicionable();
		
		ControladorCampoBatalla.set(copia1,copiaDib1);
		ControladorCampoBatalla.set(copia2,copiaDib2);
		
		this.getGameLoop().agregar(copiaDib1);
		this.getGameLoop().agregar(copiaDib2);
		
		JOptionPane.showMessageDialog(getFrame(), "Alucinación realizada exitosamente", "Ataque Exitoso",JOptionPane.INFORMATION_MESSAGE);
		
	}


	@Override
	public void seGanoPartida() {
		 JOptionPane.showMessageDialog(getFrame(), "Has ganado la partida!");
		 System.exit(0);
		 
	}
	
	@Override
	public void sePerdioPartida() {
		 JOptionPane.showMessageDialog(getFrame(), "Has perdido la partida!");
		 System.exit(0);
		 
	}



	@Override
	public void seCreoCopiaFicticia(Elemento elemento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seCreoVolcan(Elemento elemento) throws FueraDeRangoException,
			PosicionInvalidaException, IOException {
		//final UnidadModelo modelo = new UnidadModelo();
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaGas(elemento);
		this.getGameLoop().agregar(imagen);	
		
	}

	public JFrame getFrame() {
		return frame;
	}

	@SuppressWarnings("static-access")
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void seCreoCristal(Cristal cristal) throws IOException, FueraDeRangoException, PosicionInvalidaException {
		this.getGameLoop().agregar(cristal);
		Imagen imagen = new VistaCristal(cristal);
		this.getGameLoop().agregar(imagen);	
		this.controladorCampoBatalla.agregarAMapaDeVistaDeElementos(cristal, imagen);
		
	}

	@Override
	public void seCreoCentroDeComandoTerran(ElementoArtificial elemento) throws FueraDeRangoException, PosicionInvalidaException, IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaCentroComandoTerran(elemento);
		this.getGameLoop().agregar(imagen);
		
	}

	@Override
	public void seMurioUnaUnidad(Elemento elemento) throws ElementoNoEncontradoException {
		// TODO Auto-generated method stub
		this.gameLoop.remover(elemento);
		this.gameLoop.remover(this.gameLoop.getObjetoDibujable(elemento.getPosicion()));
	}

	@Override
	public void seMurioUnaUnidad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seCreoCentroDeComandoProtoss(ElementoArtificial elemento)
			throws FueraDeRangoException, PosicionInvalidaException,
			IOException {
		this.getGameLoop().agregar(elemento);
		Imagen imagen = new VistaCentroComandoProtoss(elemento);
		this.getGameLoop().agregar(imagen);
	}

	@Override
	public void seCreoCentroDeComandos(ElementoArtificial centroComandoJugadorActual, Raza raza) throws IOException {
		Imagen vista = raza.obtenerCentroDeComandos(centroComandoJugadorActual);
		this.getGameLoop().agregar(vista);
	}
}

