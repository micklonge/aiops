package log.parsing.main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import log.parsing.nn.train.panel.PanelTrainNNMain;
import log.parsing.nn.train.panel.core.TrainNNFSM;
import log.parsing.nn.train.test.panel.TestMainJPanel;
import log.parsing.preprocess.panel.PanelPreprocess;
import log.parsing.ui.core.UIEventInterface;
import log.parsing.ui.core.UICoreFSM;

public class PanelLogParsingTrainMain extends JPanel {
	
	private static final long serialVersionUID = 2002012308498717087L;
	
	protected BlockingQueue<UIEventInterface> eventQueue = new LinkedBlockingQueue<UIEventInterface>();
	
	private UICoreFSM coreFSM = new UICoreFSM(eventQueue);
	
	private PanelPreprocess uiLogContent = null;
	
	private TrainNNFSM trainNNFSM = new TrainNNFSM(eventQueue);
	private PanelTrainNNMain trainNNMainJPanel = null;
	
	private TestMainJPanel testMainJPanel = null;
	
	private final static int FirstLayerHeight = 450;
	
	public PanelLogParsingTrainMain() throws Exception {
		SpringLayout tspLayout = new SpringLayout();
		setLayout(tspLayout);
		
		// log: origin normalizing template
		uiLogContent = new PanelPreprocess();
		add(uiLogContent);
		tspLayout.putConstraint(SpringLayout.NORTH, uiLogContent, 10, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.SOUTH, uiLogContent, FirstLayerHeight, SpringLayout.NORTH, uiLogContent);
		tspLayout.putConstraint(SpringLayout.WEST, uiLogContent, 10, SpringLayout.WEST, this);
		tspLayout.putConstraint(SpringLayout.EAST, uiLogContent, 1780, SpringLayout.WEST, uiLogContent);
		
		trainNNMainJPanel = new PanelTrainNNMain(trainNNFSM);
		add(trainNNMainJPanel);
		tspLayout.putConstraint(SpringLayout.NORTH, trainNNMainJPanel, 10, SpringLayout.SOUTH, uiLogContent);
		tspLayout.putConstraint(SpringLayout.SOUTH, trainNNMainJPanel, -10, SpringLayout.SOUTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, trainNNMainJPanel, 10, SpringLayout.WEST, this);
		tspLayout.putConstraint(SpringLayout.EAST, trainNNMainJPanel, 1500, SpringLayout.WEST, trainNNMainJPanel);
		
		testMainJPanel = new TestMainJPanel();
		add(testMainJPanel);
		tspLayout.putConstraint(SpringLayout.NORTH, testMainJPanel, 10, SpringLayout.SOUTH, uiLogContent);
		tspLayout.putConstraint(SpringLayout.SOUTH, testMainJPanel, -10, SpringLayout.SOUTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, testMainJPanel, 10, SpringLayout.EAST, trainNNMainJPanel);
		tspLayout.putConstraint(SpringLayout.EAST, testMainJPanel, -10, SpringLayout.EAST, this);
		
		coreFSM.addCoreModuleInfo(trainNNFSM.getModuleName(), trainNNFSM);
		
		new Thread(coreFSM).start();
	}

}
