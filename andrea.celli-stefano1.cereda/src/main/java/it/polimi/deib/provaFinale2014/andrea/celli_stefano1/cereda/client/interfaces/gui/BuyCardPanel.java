package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel.objectsOfGame.Card;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuyCardPanel extends JPanel {

	BuyCardManager listener;

	public BuyCardPanel(ArrayList<Card> buyableCards, BuyCardManager listener,
			int choicePanelWidth, int choicePanelHeight) {

		this.listener = listener;

		this.setLayout(null);

		this.setLocation(choicePanelWidth, choicePanelHeight);
		this.setSize(choicePanelWidth, choicePanelHeight);
		listener.getGameMap().add(this, 0);
		this.setVisible(true);

		switch (buyableCards.size()) {

		case (0):
			JLabel label = new JLabel("No buyable cards");
			label.setSize(choicePanelWidth, choicePanelHeight / 3);
			label.setLocation(0, choicePanelHeight / 3);
			this.add(label);
			break;
		case (1):
			JButton button = new JButton(buyableCards.get(0).toString());
			button.addActionListener(listener);
			button.setSize(choicePanelWidth, choicePanelHeight / 3);
			button.setLocation(0, choicePanelHeight / 3);
			button.setOpaque(true);
			this.add(button);
			button.setVisible(true);
			break;
		case (2):
			JButton button1 = new JButton(buyableCards.get(0).toString());
			JButton button2 = new JButton(buyableCards.get(1).toString());
			button1.addActionListener(listener);
			button2.addActionListener(listener);
			button1.setSize(choicePanelWidth, choicePanelHeight / 2);
			button2.setSize(choicePanelWidth, choicePanelHeight / 2);
			button1.setLocation(0, 0);
			button2.setLocation(0, choicePanelHeight / 2);
			button1.setOpaque(true);
			button2.setOpaque(true);
			this.add(button1, 0);
			this.add(button2, 0);
			button1.setVisible(true);
			button2.setVisible(true);
			break;
		}
	}

}
