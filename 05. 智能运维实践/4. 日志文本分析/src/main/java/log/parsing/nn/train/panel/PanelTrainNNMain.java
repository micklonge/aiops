package log.parsing.nn.train.panel;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import log.parsing.nn.train.panel.core.TrainNNFSM;

public class PanelTrainNNMain extends JPanel {

	private static final long serialVersionUID = -670249879201809409L;
	
	private static final int labelWidth = 120;
	private static final int textWidth = 200;
	
	private PanelTrainNNResult trainNNResultJPanel = null;
	private PanelTrainNNConfigure trainNNConfigureJPanel = null;
	
	public PanelTrainNNMain(TrainNNFSM trainNNFSM) {
		SpringLayout tspLayout = new SpringLayout();
		setLayout(tspLayout);
		
		trainNNConfigureJPanel = new PanelTrainNNConfigure(trainNNFSM);
		add(trainNNConfigureJPanel);
		tspLayout.putConstraint(SpringLayout.NORTH, trainNNConfigureJPanel, 0, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.SOUTH, trainNNConfigureJPanel, 0, SpringLayout.SOUTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, trainNNConfigureJPanel, 0, SpringLayout.WEST, this);
		tspLayout.putConstraint(SpringLayout.EAST, trainNNConfigureJPanel, 600, SpringLayout.WEST, trainNNConfigureJPanel);
		
		trainNNResultJPanel = new PanelTrainNNResult(trainNNFSM, labelWidth, textWidth);
		add(trainNNResultJPanel);
		tspLayout.putConstraint(SpringLayout.NORTH, trainNNResultJPanel, 0, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.SOUTH, trainNNResultJPanel, 0, SpringLayout.SOUTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, trainNNResultJPanel, 3, SpringLayout.EAST, trainNNConfigureJPanel);
		tspLayout.putConstraint(SpringLayout.EAST, trainNNResultJPanel, 0, SpringLayout.EAST, this);
		
		trainNNFSM.setTrainNNJPanel(trainNNConfigureJPanel, trainNNResultJPanel);
	}

}
