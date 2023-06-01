package framework.ai.nn;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import framework.ai.nn.NNObject.NNLayer;
import framework.ai.nn.NNObject.NNRelation;
import framework.ai.nn.activation.NNActivationEnum;
import framework.ai.nn.activation.NNActivationFactory;
import framework.ai.nn.weights.NNWeightEnum;
import log.parsing.nn.train.core.TrainNNData;

public class NNFramework {
	
	private static final Logger logger = LogManager.getLogger();
	
	public static List<Double> compute(NNObject nnObject, List<Double> inputList, List<Double> targetOutputList, 
			NNActivationEnum nnAcitvationEnum, NNWeightEnum nnWeightEnum, double a, double multiple) throws Exception {
		int i, j, k;
		double sum = 0.0;
		
		NNLayer preNNData = null;
		NNLayer curNNData = null;
		
		NNRelation nnRelation = null;
		
		if (nnObject == null) {
			logger.error("the nnObject is null");
			return null;
		}

		nnObject.initInput(inputList);
		for (i = 1; i <= nnObject.getNumLayers(); ++i) {
			preNNData = nnObject.getNNData(i - 1);
			curNNData = nnObject.getNNData(i);
			
			curNNData.getxList().clear();
			curNNData.getyList().clear();
			
			nnRelation = nnObject.getNNRelation(i - 1);
			
			for (j = 0; j < nnRelation.getOutput(); ++j) {
				sum = 0.0;
				for (k = 0; k < nnRelation.getInput() - 1; ++k) {
					sum += preNNData.getyList().get(k) * nnRelation.getWeightListList().get(j).get(k);
				}
				sum += preNNData.getBias() * nnRelation.getWeightListList().get(j).get(k);
				curNNData.addX(sum);
				curNNData.addY(NNActivationFactory.compute(nnAcitvationEnum, sum, a, multiple));
			}
			
		}
		
		if (targetOutputList != null) {
			NNActivationFactory.backPropagate(nnAcitvationEnum, nnWeightEnum, nnObject, targetOutputList, nnObject.getOutputList());
		}
		
		return nnObject.getOutputList();
	}
	
	public static NNComputeResult computeWithTag(String log) throws Exception {
		NNComputeResult nnComputeResult = new NNComputeResult();
		
		List<Double> outputList = NNFramework.compute(
				TrainNNData.getNNObject(), TrainNNData.getTrainNNDataDictionary().formatInputList(log), 
				null, NNActivationEnum.Sigmoid, NNWeightEnum.Momentum, 0, 0);
		int pos = -1;
		double maxMatch = 0.0;
		for (int i = 0; i < outputList.size(); ++i) {
			if (maxMatch < outputList.get(i)) {
				maxMatch = outputList.get(i);
				pos = i;
			}
		}
		
		nnComputeResult.setTag(TrainNNData.getTrainNNDataDictionary().getResults(pos));
		nnComputeResult.setMatch(maxMatch);
		
		return nnComputeResult;
	}
	
	public static class NNComputeResult {
		private String tag = null;
		private Double match = null;
		
		public void setTag(String tag) {
			this.tag = tag;
		}
		
		public String getTag() {
			return tag;
		}
		
		public void setMatch(double match) {
			this.match = match;
		}
		
		public double getMatch() {
			return match;
		}
	}
	
}
