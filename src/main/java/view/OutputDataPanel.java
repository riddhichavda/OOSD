package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import constants.Constants;
import lombok.Getter;

public class OutputDataPanel extends JPanel{
	
	private @Getter TitledBorder outputDataPanelTitle;
	
	public OutputDataPanel(){
		
		outputDataPanelTitle = BorderFactory.createTitledBorder("Output Data");
		this.setPreferredSize(new Dimension(Constants.MAZEFRAMEWIDTH,  Constants.MAZEFRAMEHEIGHT / 6));
		this.setBackground(new Color(223, 247, 168));
		this.setBorder(outputDataPanelTitle);
		this.setVisible(true);
	}

}
