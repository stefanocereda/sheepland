package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import javax.swing.JLayeredPane;

/**
 * This Layered Pane contains three JPanels: the visualized map,the layer that
 * displays the animals and the "painted" map. Its main goal is to guarantee
 * that every change in size or position of the visualized map is followed by an
 * equal change in the hidden map and in the position of the animals. This in
 * necessary to maintain alligment in all the maps.
 * 
 * The order of the panel is: 1) the panel that displays sheeps and shepherds 2)
 * the panel that displays the map 3) the hidden painted map
 * 
 * @author Andrea
 * 
 */
public class MapBoard extends JLayeredPane {

}
