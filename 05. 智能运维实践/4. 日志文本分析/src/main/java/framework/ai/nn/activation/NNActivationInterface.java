package framework.ai.nn.activation;

import java.util.List;

import framework.ai.nn.NNObject;
import framework.ai.nn.weights.NNWeightEnum;

public interface NNActivationInterface {
	
	public double activation(double x, double a, double multiple);
	public void backPropagate(NNObject nnObject, NNWeightEnum nnWeightEnum, List<Double> targetOutputList, List<Double> outputList) throws Exception;

}
