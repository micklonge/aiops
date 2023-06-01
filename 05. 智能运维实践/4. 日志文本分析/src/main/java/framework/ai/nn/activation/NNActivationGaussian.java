package framework.ai.nn.activation;

import java.util.List;

import framework.ai.nn.NNObject;
import framework.ai.nn.weights.NNWeightEnum;

public class NNActivationGaussian implements NNActivationInterface {

	@Override
	public double activation(double x, double a, double multiple) {
		return Math.exp(-x*x);
	}
	
	@Override
	public void backPropagate(NNObject nnObject, NNWeightEnum nnWeightEnum, List<Double> targetOutputList, List<Double> outputList)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
