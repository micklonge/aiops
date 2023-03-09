package enn.monitor.ai.nn.activation;

import java.util.List;

import enn.monitor.ai.nn.NNObject;

public class NNActivationSoftPlus implements NNActivationInterface {

	@Override
	public double activation(double x, double a, double multiple) {
		return Math.log(1 + Math.exp(x));
	}
	
	@Override
	public void backPropagate(NNObject nnObject, List<Double> targetOutputList, List<Double> outputList)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
