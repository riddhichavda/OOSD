package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import constants.Constants;

// I would suggest not to create classes for panels inside left panel 

public class ButtonPanel extends JPanel{
	
	JButton downBtn;
	JButton eatBtn;
	JButton leftBtn;
	JButton rightBtn;
	JButton upBtn;
	
	public ButtonPanel() {
		this.setPreferredSize(new Dimension(Constants.MAZEFRAMEWIDTH,  Constants.MAZEFRAMEHEIGHT / 6));
		this.setBackground(new Color(223, 247, 168));
		this.setVisible(true);
		init();
	}

	public void addBtnCreateListener(ActionListener listener) {
		upBtn.addActionListener(listener);
		downBtn.addActionListener(listener);
		leftBtn.addActionListener(listener);
		rightBtn.addActionListener(listener);
		eatBtn.addActionListener(listener);
	}
	
	private void init() {
		upBtn = new JButton("UP");
		downBtn = new JButton("DOWN");
		leftBtn = new JButton("LEFT");
		rightBtn = new JButton("RIGHT");
		eatBtn = new JButton("EAT");
		
		this.add(upBtn);
		this.add(downBtn);
		this.add(leftBtn);
		this.add(rightBtn);
		this.add(eatBtn);
		
		this.setVisible(true);
	}
		
}
