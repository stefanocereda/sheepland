package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates.BlackSheepPositions;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates.LambPositions;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates.RamPositions;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates.SheepPositions;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui.MapCoordinates.WolfPositions;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Road;
import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Terrain;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains utility methods used in the Linker class to calculate the
 * origin points for the labels displaying sheeps ecc.
 * 
 * It takes the initial points from the enums containing the coordinates of the
 * original image. Knowing the actual dimension of the map it performs a scaling
 * operation and finds the new points.
 * 
 * @author Andrea
 * @TODO refactor (tutti i metodi sono molto simili)
 * 
 */
public class CoordinatesConverter {

	// the dimension of the map
	private int width, height;

	// the constants used to perform the conversion (they are calculated from
	// the map dimension)
	private double kx, ky;

	public CoordinatesConverter(Dimension mapDimension) {
		this.width = mapDimension.width;
		this.height = mapDimension.height;

		kx = width / GuiConstants.MAP_WIDTH;
		ky = height / GuiConstants.MAP_HEIGHT;
	}

	/**
	 * This method creates an hashMap containing the origin of sheep's label for
	 * each terrain
	 * 
	 * @return hashMap of terrain/sheep's origins
	 * */
	public Map<Terrain, Point> calculateSheepOrigins() {
		HashMap<Terrain, Point> hashMap = new HashMap<Terrain, Point>();

		for (SheepPositions position : SheepPositions.values()) {
			hashMap.put(position.getTerrain(), new Point(
					(int) (position.getX() * kx), (int) (position.getY() * ky)));
		}

		return hashMap;
	}

	/**
	 * This method creates an hashMap containing the origin of lamb's label for
	 * each terrain
	 * 
	 * @return hashMap of terrain/lamb's origins
	 * */

	public Map<Terrain, Point> calculateLambOrigins() {
		HashMap<Terrain, Point> hashMap = new HashMap<Terrain, Point>();

		for (LambPositions position : LambPositions.values()) {
			hashMap.put(position.getTerrain(), new Point(
					(int) (position.getX() * kx), (int) (position.getY() * ky)));
		}

		return hashMap;
	}

	/**
	 * This method creates an hashMap containing the origin of ram's label for
	 * each terrain
	 * 
	 * @return hashMap of terrain/ram's origins
	 * */

	public Map<Terrain, Point> calculateRamOrigins() {
		HashMap<Terrain, Point> hashMap = new HashMap<Terrain, Point>();

		for (RamPositions position : RamPositions.values()) {
			hashMap.put(position.getTerrain(), new Point(
					(int) (position.getX() * kx), (int) (position.getY() * ky)));
		}

		return hashMap;
	}

	/**
	 * This method creates an hashMap containing the origin of black sheep's
	 * label for each terrain
	 * 
	 * @return hashMap of terrain/black sheep's origins
	 * */

	public Map<Terrain, Point> calculateBlackSheepOrigins() {
		HashMap<Terrain, Point> hashMap = new HashMap<Terrain, Point>();

		for (BlackSheepPositions position : BlackSheepPositions.values()) {
			hashMap.put(position.getTerrain(), new Point(
					(int) (position.getX() * kx), (int) (position.getY() * ky)));
		}

		return hashMap;
	}

	/**
	 * This method creates an hashMap containing the origin of wolf's label for
	 * each terrain
	 * 
	 * @return hashMap of terrain/wolf's origins
	 * */

	public Map<Terrain, Point> calculateWolfOrigins() {
		HashMap<Terrain, Point> hashMap = new HashMap<Terrain, Point>();

		for (WolfPositions position : WolfPositions.values()) {
			hashMap.put(position.getTerrain(), new Point(
					(int) (position.getX() * kx), (int) (position.getY() * ky)));
		}

		return hashMap;
	}

	// @TODO
	public Map<Road, Point> calculatePawnOrigins() {
		// TODO Auto-generated method stub
		return null;
	}

}
