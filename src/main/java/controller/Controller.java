package controller;

import view.GridView;
import view.MainFrame;
import view.MazeCreator;

public class Controller {
	private AgentActions agentActions;
	private BtnCreateController btnCreateController;
	private GridView gridView;
	private MainFrame mainFrame;
	private MazeCreator mazeCreator;
	private NonGuiListener nonGuiListener;
	private ParametersCalculator parametersCalculator;

	// private MouseListenerController mouseListenerController;
	public Controller() {
		mazeCreator = new MazeCreator();
		gridView = new GridView();
		btnCreateController = new BtnCreateController(mazeCreator.getInputPanel(), gridView,
				mazeCreator.getButtonPanel());
		mainFrame = new MainFrame(mazeCreator, gridView);
		agentActions = AgentActions.getInstance();
		agentActions.setGridView(gridView);
		nonGuiListener = new NonGuiListener(mazeCreator.getInputPanel(), gridView);
		parametersCalculator = new ParametersCalculator();
		// mouseListenerController = new MouseListenerController(gridView);
	}

}
