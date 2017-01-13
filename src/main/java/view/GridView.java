package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import constants.Constants;
import lombok.Getter;
import lombok.Setter;
import model.Agent;
import model.GridCell;

public class GridView extends JPanel implements MouseListener, MouseMotionListener {
	// variable to store length and breadth of the created maze
	private @Getter @Setter ArrayList<Integer> finalizedMazeCells;
	private @Getter @Setter GridCell gridCell;
	private @Getter @Setter boolean inUIMode = false;
	private @Setter Rectangle[] mazeCell;
	public boolean mazeFinalized = false;
	public boolean mazeSetFlag = false;
	private @Getter int pos;
	public boolean rewardSetFlag = false;
	private @Getter @Setter int selectedRectangle;
	private @Getter @Setter Agent singleAgent;
	private @Getter @Setter boolean[] visibility;

	public GridView() {
		this.setPreferredSize(new Dimension(Constants.RIGHTPANELWIDTH, Constants.RIGHTPANELHEIGHT));
		this.setBounds(Constants.MAINFRAMEWIDTH / 3 + 1, 0, Constants.RIGHTPANELWIDTH, Constants.RIGHTPANELHEIGHT);
		this.setVisible(true);
		init();
		addMouseListener(this);
		addMouseMotionListener(this);
		requestFocus();
	}

	public void addAgent(Agent a) {
		// agents.add(a);
		singleAgent = a;
	}

	public void addGridCell(GridCell gridCell) {
		this.gridCell = gridCell;
	}

	public void clearSelection() {
		Arrays.fill(visibility, false);
		singleAgent = new Agent();
		// Arrays.fill(gridCell.getReward(), -1);
	}

	public void init() {
		singleAgent = new Agent();
		pos = -1; // Default out of Array index
		finalizedMazeCells = new ArrayList<Integer>();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		if (mazeFinalized == false && gridCell != null) {
			for (int i = 0; i < mazeCell.length; i++) {
				if (mazeCell[i].contains(mouseX, mouseY)) {
					if (i != pos)
						visibility[i] = !visibility[i];
					pos = i;
					repaint();

				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		if (mazeFinalized == true) {
			for (int i = 0; i < mazeCell.length; i++) {
				if (mazeCell[i].contains(mouseX, mouseY)) {
					pos = i;
					selectedRectangle = i;
					repaint();
				}
			}
		} else if (gridCell != null) {
			for (int i = 0; i < mazeCell.length; i++) {
				if (mazeCell[i].contains(mouseX, mouseY)) {
					if (i != pos)
						visibility[i] = !visibility[i];
					pos = i;
					selectedRectangle = i;
					repaint();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (mazeSetFlag == true) {
			for (int i = 0; i < mazeCell.length; i++) {
				if (visibility[i]) {

					if (mazeFinalized && i == this.getSelectedRectangle()) {
						g.setColor(Color.GREEN);
						g.fillRect(mazeCell[i].x - 1, mazeCell[i].y - 1, mazeCell[i].width + 2, mazeCell[i].height + 2);
						g.setColor(Color.DARK_GRAY);
						g.fillRect(mazeCell[i].x + 4, mazeCell[i].y + 4, mazeCell[i].width - 9, mazeCell[i].height - 9);
					} else {
						g.setColor(Color.DARK_GRAY);
						g.fillRect(mazeCell[i].x, mazeCell[i].y, mazeCell[i].width - 1, mazeCell[i].height - 1);
					}
					if (!finalizedMazeCells.contains(i)) {
						finalizedMazeCells.add(i);
					}
				} else if (!inUIMode) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(mazeCell[i].x, mazeCell[i].y, mazeCell[i].width - 1, mazeCell[i].height - 1);

				}
			}

			if (mazeFinalized) {
				for (int i = 0; i < gridCell.getMazeCell().length; i++) {
					int position = singleAgent.getPos();
					if (position == i) {
						g.setColor(Color.RED);
						g.fillOval(mazeCell[position].x + (mazeCell[position].width / 2),
								mazeCell[position].y + (mazeCell[position].height / 2),
								(int) (0.25 * mazeCell[position].width - 1),
								(int) (0.25 * mazeCell[position].width - 1));
					}
					g.setColor(Color.WHITE);
					g.setFont(new Font("Verdana", Font.BOLD, 20));
					if (gridCell.getReward().containsKey(i) && gridCell.getVisibility()[i])
						g.drawString(gridCell.getReward().get(i).getValue() + "", mazeCell[i].x + 40,
								mazeCell[i].y + 40);

				}

			}

		}
	}
}