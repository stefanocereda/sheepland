package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.LambPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.PiecesOnTheMap;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.RamPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.SheepPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.pieces.WolfPanel;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * This panel displays the game map. The map contains all the smaller Labels
 * displaying sheep ecc.
 * 
 * @author Andrea
 * 
 */

public class Map extends JPanel {

	// the image to be displayed
	Image img;

	/**
	 * This hashMap contains, for each terrain, an array list containing all the
	 * smaller JLabel that are displayed on the map.
	 */
	private HashMap<Terrain, ArrayList<PiecesOnTheMap>> components = new HashMap<Terrain, ArrayList<PiecesOnTheMap>>();

	/**
	 * This class calculates and stores the dimension that each type pf label
	 * has to have
	 */
	private DimensionCalculator dimensionCalculator;

	/**
	 * The class that serves as Listener to perform Drag and Drop
	 */
	private DragAndDrop listener;

	/**
	 * The map dimension
	 */
	private Dimension mapDimension;

	/**
	 * The linker. Used to understand where to diplay new JPanels
	 */
	private Linker linker;

	//
	//
	//
	//
	// METHODS
	//
	//
	//
	//
	/**
	 * Initially the map is empty. All the "sub-panels" will be added after the
	 * first board status is received by the player.
	 * 
	 */
	public Map() {
		super();

		// create first an image icon (the source is places in a source folder)
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(
				"/Game_Board.png"));
		// get the displayable image from the image icon
		img = imgIcon.getImage();
		setOpaque(true);
	}

	/**
	 * Draw the image of the map.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// set the border color of the panel
		setBackground(GuiConstants.SEACOLOR);
		Graphics2D g2d = (Graphics2D) g;
		// paint the image so that it's entirely displayed in the panel
		g2d.drawImage(img,
				(this.getWidth() - (img.getWidth(null) * this.getHeight())
						/ img.getHeight(null)) / 2, 0,
				(img.getWidth(null) * this.getHeight()) / img.getHeight(null),
				getHeight(), null);
	}

	public Dimension getMapDimension() {
		return mapDimension;
	}

	/**
	 * Set the reference to the linker and initialize it.
	 * 
	 * Creates the Verifier.
	 * 
	 * Creates the DragAndDrop class and adds the listeners.
	 * 
	 * Creates an instance of Dimension Calculator (it can't be initialized in
	 * the constructor beacuse it needs the dimension of the panels that the
	 * constructor "is building")
	 * 
	 * @param interfaceGUI
	 */
	public void initMapComponents(InterfaceGui interfaceGui) {

		// calculates the map dimension
		mapDimension = new Dimension(
				(GuiConstants.MAP_WIDTH * this.getHeight())
						/ GuiConstants.MAP_HEIGHT, this.getHeight());

		linker = Linker.getLinkerInsance();
		linker.initLinker(interfaceGui.getGameController().getBoardStatus(),
				mapDimension);

		// the map doesn't hold a reference to the verifier. It just gives it as
		// a parameter to the drag&drop listener
		DragAndDropManager dragAndDropManager = new DragAndDropManager(
				interfaceGui, this);

		listener = new DragAndDrop(this, dragAndDropManager);

		// add the listeners
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);

		// create an instance of dimensionCalculator giving the displayed map
		// dimension
		dimensionCalculator = new DimensionCalculator(mapDimension);
	}

	public HashMap<Terrain, ArrayList<PiecesOnTheMap>> getComponentsInTerrains() {
		return components;
	}

	public Linker getLinker() {
		return linker;
	}

	/**
	 * adds a sheep panel to a specific terrain
	 * 
	 * if a panel displaying sheeps is already in the specified terrain it
	 * updates the number of sheeps
	 * 
	 * if there wasn't any sheep panel on the terrain it creates a new one
	 * 
	 * @param terrain
	 */
	public void addSheep(Terrain terrain) {

		int currentNumberOfSheep = linker.getSheepForEachTerrain().get(terrain);
		// if there are no sheep in the tarrain
		if (currentNumberOfSheep == 0) {

			SheepPanel newSheep = new SheepPanel(
					ImagePathCreator.findSheepPath(1),
					dimensionCalculator.getSheepDimension());
			this.add(newSheep);
			newSheep.setLocation(linker.getSheepOrigins().get(terrain));
			setVisible(true);

			// add this JPanel in the array list of panels displayed in the
			// terrain
			components.get(terrain).add(newSheep);
		} else {
			// set the new image in the sheep panel
			for (PiecesOnTheMap panel : components.get(terrain)) {
				if (panel instanceof SheepPanel) {
					// set the new image
					panel.setImg(ImagePathCreator
							.findSheepPath(currentNumberOfSheep + 1));
					panel.repaint();

					// update the sheep counter
					linker.getSheepForEachTerrain().put(terrain,
							currentNumberOfSheep + 1);
				}
			}
		}
	}

	/**
	 * adds a ram to a certain terrain
	 * 
	 * if a ram panel is already in the terrain it updates it. Otherwise it
	 * creates a new one.
	 * 
	 * @param terrain
	 */
	public void addRam(Terrain terrain) {
		int currentNumberOfRam = linker.getRamForEachTerrain().get(terrain);

		if (currentNumberOfRam == 0) {
			RamPanel newRam = new RamPanel(ImagePathCreator.findRamPath(1),
					dimensionCalculator.getRamDimension());
			this.add(newRam);
			newRam.setLocation(linker.getRamOrigins().get(terrain));
			setVisible(true);

			components.get(terrain).add(newRam);
		} else {
			for (PiecesOnTheMap panel : components.get(terrain)) {
				if (panel instanceof RamPanel) {
					// set the new image
					panel.setImg(ImagePathCreator
							.findRamPath(currentNumberOfRam + 1));
					panel.repaint();

					// update the sheep counter
					linker.getSheepForEachTerrain().put(terrain,
							currentNumberOfRam + 1);
				}
			}
		}
	}

	/**
	 * adds a lamb to a certain terrain
	 * 
	 * if a lamb panel is already in the terrain it updates it. Otherwise it
	 * creates a new one.
	 * 
	 * @param terrain
	 */
	public void addLamb(Terrain terrain) {
		int currentNumberOfLamb = linker.getLambForEachTerrain().get(terrain);

		if (currentNumberOfLamb == 0) {
			LambPanel newLamb = new LambPanel(ImagePathCreator.findLambPath(1),
					dimensionCalculator.getLambDimension());
			this.add(newLamb);
			newLamb.setLocation(linker.getLambOrigins().get(terrain));
			setVisible(true);

			components.get(terrain).add(newLamb);
		} else {
			for (PiecesOnTheMap panel : components.get(terrain)) {
				if (panel instanceof LambPanel) {
					// set the new image
					panel.setImg(ImagePathCreator
							.findLambPath(currentNumberOfLamb + 1));
					panel.repaint();

					// update the sheep counter
					linker.getSheepForEachTerrain().put(terrain,
							currentNumberOfLamb + 1);
				}
			}
		}
	}

	/**
	 * This method adds a wolf to the specified terrain (it creates a new
	 * instace of WolfPanel)
	 * 
	 * @param terrain
	 */
	public void addWolf(Terrain terrain) {
		WolfPanel wolf = new WolfPanel(GuiConstants.WOLF,
				dimensionCalculator.getWolfDimension());
		this.add(wolf);
		wolf.setLocation(linker.getWolfOrigins().get(terrain));
		wolf.setVisible(true);

		components.get(terrain).add(wolf);
	}
}
