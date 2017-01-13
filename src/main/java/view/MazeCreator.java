package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import constants.Constants;
import lombok.Getter;

/*This class is the left panel in the view where you set all the parameters, view the output, buttons. 
 * This panel contain 3 sub panels: Input, output, buttons*/

public class MazeCreator extends JPanel {

	private @Getter ButtonPanel buttonPanel = new ButtonPanel();
	private @Getter InputPanel inputPanel = new InputPanel();
	private @Getter TitledBorder inputPanelTitle;

	public MazeCreator() {

		init();
	}

	public void init() {

		this.setBounds(0, 0, Constants.MAINFRAMEWIDTH / 3, Constants.MAINFRAMEHEIGHT);
		// these titles can be changed later
		inputPanelTitle = BorderFactory.createTitledBorder("Inputs");
		inputPanel.setPreferredSize(new Dimension(Constants.MAINFRAMEWIDTH / 3, Constants.MAINFRAMEHEIGHT));
		inputPanel.setBackground(new Color(200, 200, 220));
		inputPanel.setBorder(inputPanelTitle);
		this.add(inputPanel);

		this.setVisible(true);
		this.repaint();

	}
}
