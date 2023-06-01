package log.parsing.nn.train.panel.core;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import framework.ai.nn.NNObject;
import log.parsing.nn.train.core.TrainNNDataDictionary;
import log.parsing.nn.train.core.TrainNNData;
import log.parsing.nn.train.panel.PanelTrainNNConfigure;
import log.parsing.nn.train.panel.PanelTrainNNResult;
import log.parsing.nn.train.panel.parameter.PanelTrainNNParameter;
import log.parsing.nn.train.panel.parameter.PanelTrainNNResultParameter;
import log.parsing.nn.train.panel.parameter.PanelTrainNNParameter.NNItem;
import log.parsing.ui.core.UIEventInterface;
import log.parsing.ui.core.UIFSMInterface;

public class TrainNNFSM extends UIFSMInterface {
	
	private static final Logger logger = LogManager.getLogger();
	
	private TrainNNModeEnum currentModeEnum = TrainNNModeEnum.Ready;
	
	private NNObject nnObject = null;
	
	private PanelTrainNNConfigure trainNNConfigureJPanel = null;
	private PanelTrainNNResult trainNNResultJPanel = null;
	
	private TrainNNDataDictionary trainNNData = new TrainNNDataDictionary();
	private TrainNNCore nnCore = null;
	
	public TrainNNFSM(BlockingQueue<UIEventInterface> eventQueue) {
		super("TrainNN");
		this.eventQueue = eventQueue;
		nnCore = new TrainNNCore(this);
	}
	
	@Override
	public void runEvent(Object data) throws Exception {
		TrainNNEventEnum trainNNEventEnum = null;
		TrainNNEvent trainNNEvent = (TrainNNEvent) data;
		
		PanelTrainNNParameter trainNNParameter = null;
		
		trainNNEventEnum = trainNNEvent.getEventEnum();
		switch (currentModeEnum) {
		case Ready:
			switch (trainNNEventEnum) {
			case Train:
				currentModeEnum = TrainNNModeEnum.Train;
				trainNNParameter = (PanelTrainNNParameter) trainNNEvent.getData();
				train(trainNNParameter);
				break;
			default:
				logger.error("the current mode is " + currentModeEnum.name() + ", the event is " + trainNNEventEnum.name());
				break;
			}
			break;
		case Train:
			switch (trainNNEventEnum) {
			case Reset:
				nnCore.stop();
				currentModeEnum = TrainNNModeEnum.Ready;
				trainNNResultJPanel.computeResult();
				break;
			case TrainInfo:
				trainNNConfigureJPanel.setTrainNNInfo((PanelTrainNNResultParameter)trainNNEvent.getData());
				break;
			default:
				logger.error("the current mode is " + currentModeEnum.name() + ", the event is " + trainNNEventEnum.name());
				break;
			}
			break;
		default:
			logger.error("unexpected TrainNNModeEnum, it is " + currentModeEnum.name());
			break;
		}
	}
	
	private void train(PanelTrainNNParameter trainNNParameter) {
		nnObject = new NNObject();
		
		trainNNData.clear();
		trainNNData.addTrainData("Normal", "");
		for(Map.Entry<String, String> entry : TrainNNData.getTemplateMap().entrySet()) {
			trainNNData.addTrainData(entry.getValue(), entry.getKey());
		}
		
		nnObject.addNeuronsLayer(trainNNData.getInputSize(), trainNNParameter.getBias());
		for (NNItem item : trainNNParameter.getNNLayer()) {
			nnObject.addNeuronsLayer(item.numberOfNeuron, trainNNParameter.getBias());
		}
		nnObject.setLearningRate(trainNNParameter.getLearningRate());
		nnObject.setMomentum(trainNNParameter.getMomentum());
		nnObject.setMaxNoise(trainNNParameter.getMaxNoise());
		nnObject.addNeuronsLayer(trainNNData.getOutputSize(), trainNNParameter.getBias());
		
		nnCore.setTrainNNData(trainNNData);
		nnCore.setNnObject(nnObject);
		nnCore.setInputListList(trainNNData.getInputListList());
		nnCore.setTargetOutputList(trainNNData.getOutputList());
		nnCore.setTrainNNParameter(trainNNParameter);
		
		nnCore.train();
	}

	public void setTrainNNJPanel(PanelTrainNNConfigure trainNNConfigureJPanel, PanelTrainNNResult trainNNResultJPanel) {
		this.trainNNResultJPanel = trainNNResultJPanel;
		this.trainNNConfigureJPanel = trainNNConfigureJPanel;
	}
	
}
