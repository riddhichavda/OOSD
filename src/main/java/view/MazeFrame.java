package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import constants.Constants;
import lombok.Getter;

@SuppressWarnings({ "serial", "unused" })
public class MazeFrame extends JFrame {

	private ButtonPanel buttonPanel;
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
	private GridView gridView;
	private @Getter InputDataPanel inputDataPanel;
	private @Getter OutputDataPanel outputDataPanel;

	public MazeFrame(GridView gridView, ButtonPanel buttonPanel) {
		// panels
		this.gridView = gridView;
		this.buttonPanel = buttonPanel;
		inputDataPanel = new InputDataPanel();
		outputDataPanel = new OutputDataPanel();

		this.setBounds(0, 0, Constants.MAZEFRAMEWIDTH, Constants.MAZEFRAMEHEIGHT);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(perfromExitAction());
		this.setTitle("Maze Maker");

		// layout settings
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.insets = new Insets(5, 5, 5, 5);
		this.getContentPane().setLayout(gbl);

		// setting properties
		gridView.setPreferredSize(new Dimension(2 * Constants.MAZEFRAMEWIDTH / 3, Constants.MAZEFRAMEHEIGHT));
		buttonPanel.setPreferredSize(new Dimension(Constants.MAZEFRAMEWIDTH / 4, Constants.MAZEFRAMEHEIGHT / 2));
		inputDataPanel.setPreferredSize(new Dimension(Constants.MAZEFRAMEWIDTH / 4, Constants.MAZEFRAMEHEIGHT / 4));
		outputDataPanel.setPreferredSize(new Dimension(Constants.MAZEFRAMEWIDTH / 4, Constants.MAZEFRAMEHEIGHT / 4));

		this.getContentPane().add(buttonPanel, gbc);
		gbc.gridheight = 3;
		gbc.gridx++;
		this.getContentPane().add(gridView, gbc);
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy++;
		this.getContentPane().add(inputDataPanel, gbc);
		gbc.gridy++;
		this.getContentPane().add(outputDataPanel, gbc);

		// .setPreferredSize(new Dimension(Constants.mainFrameWidth / 4,
		// Constants.mainFrameHeight / 8));
		// buttonPanel.setBackground(new Color(223, 247, 168));
		this.pack();
		this.setVisible(true);
		this.repaint();
	}

	int perfromExitAction() {
		gridView.setInUIMode(false);
		return WindowConstants.DISPOSE_ON_CLOSE;
	}
}
