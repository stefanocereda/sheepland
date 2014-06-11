package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

import java.awt.Dimension;

/**
 * This class calculates and stores the size of each type of displayable JLabel.
 * 
 * @author Andrea
 * 
 */
public class DimensionCalculator {

	private Dimension sheepDimension;
	private Dimension wolfDimension;
	private Dimension lambDimension;
	private Dimension ramDimension;
	private Dimension blackSheepDimension;
	private Dimension pawnDimension;

	// coeff. to change the dimension mantaining proportions
	private double kX, kY;

	/**
	 * The constructor calculates all the values
	 * 
	 * @param mapDimension
	 *            (The dimension of the displayed Map, not the whole JPanel)
	 */
	public DimensionCalculator(Dimension mapDimension) {
		kX = mapDimension.width / GuiConstants.MAP_WIDTH;
		kY = mapDimension.height / GuiConstants.MAP_HEIGHT;

		sheepDimension = new Dimension(
				(int) (DefaultLabelDimension.SHEEP.getDefaultDimension().width * kX),
				(int) (DefaultLabelDimension.SHEEP.getDefaultDimension().height * kY));
		wolfDimension = new Dimension(
				(int) (DefaultLabelDimension.WOLF.getDefaultDimension().width * kX),
				(int) (DefaultLabelDimension.WOLF.getDefaultDimension().height * kY));
		lambDimension = new Dimension(
				(int) (DefaultLabelDimension.LAMB.getDefaultDimension().width * kX),
				(int) (DefaultLabelDimension.LAMB.getDefaultDimension().height * kY));
		ramDimension = new Dimension(
				(int) (DefaultLabelDimension.RAM.getDefaultDimension().width * kX),
				(int) (DefaultLabelDimension.RAM.getDefaultDimension().height * kY));
		blackSheepDimension = new Dimension(
				(int) (DefaultLabelDimension.BLACKSHEEP.getDefaultDimension().width * kX),
				(int) (DefaultLabelDimension.BLACKSHEEP.getDefaultDimension().height * kY));
		pawnDimension = new Dimension(
				(int) (DefaultLabelDimension.PAWN.getDefaultDimension().width * kX),
				(int) (DefaultLabelDimension.PAWN.getDefaultDimension().height * kY));
	}

	public Dimension getSheepDimension() {
		return sheepDimension;
	}

	public Dimension getWolfDimension() {
		return wolfDimension;
	}

	public Dimension getLambDimension() {
		return lambDimension;
	}

	public Dimension getRamDimension() {
		return ramDimension;
	}

	public Dimension getBlackSheepDimension() {
		return blackSheepDimension;
	}

	public Dimension getPawnDimension() {
		return pawnDimension;
	}

}
