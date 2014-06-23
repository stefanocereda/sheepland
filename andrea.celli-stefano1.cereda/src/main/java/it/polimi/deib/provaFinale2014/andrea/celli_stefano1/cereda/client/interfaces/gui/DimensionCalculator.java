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
        kX = (double) mapDimension.width / (double) GuiConstants.MAP_WIDTH;
        kY = (double) mapDimension.height / (double) GuiConstants.MAP_HEIGHT;

        sheepDimension = new Dimension(
                (int) (DefaultLabelDimension.SHEEP.getDefaultDimension().width
                        * kX * GuiConstants.SHEEP_ADJUSTING_X),
                (int) (DefaultLabelDimension.SHEEP.getDefaultDimension().height
                        * kY * GuiConstants.SHEEP_ADJUSTING_Y));
        wolfDimension = new Dimension(
                (int) (DefaultLabelDimension.WOLF.getDefaultDimension().width
                        * kX * GuiConstants.WOLF_ADJUSTING_X),
                (int) (DefaultLabelDimension.WOLF.getDefaultDimension().height
                        * kY * GuiConstants.WOLF_ADJUSTING_Y));
        lambDimension = new Dimension(
                (int) (DefaultLabelDimension.LAMB.getDefaultDimension().width
                        * kX * GuiConstants.LAMB_ADJUSTING_X),
                (int) (DefaultLabelDimension.LAMB.getDefaultDimension().height
                        * kY * GuiConstants.LAMB_ADJUSTING_Y));
        ramDimension = new Dimension(
                (int) (DefaultLabelDimension.RAM.getDefaultDimension().width
                        * kX * GuiConstants.RAM_ADJUSTING_X),
                (int) (DefaultLabelDimension.RAM.getDefaultDimension().height
                        * kY * GuiConstants.RAM_ADJUSTING_Y));
        blackSheepDimension = new Dimension(
                (int) (DefaultLabelDimension.BLACKSHEEP.getDefaultDimension().width
                        * kX * GuiConstants.BLACK_SHEEP_X),
                (int) (DefaultLabelDimension.BLACKSHEEP.getDefaultDimension().height
                        * kY * GuiConstants.BLACK_SHEEP_Y));
        pawnDimension = new Dimension(
                (int) (DefaultLabelDimension.PAWN.getDefaultDimension().width
                        * kX * GuiConstants.PAWN_ADJUSTING_X),
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
