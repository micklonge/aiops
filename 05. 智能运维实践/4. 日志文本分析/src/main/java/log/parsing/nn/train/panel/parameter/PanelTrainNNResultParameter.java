package log.parsing.nn.train.panel.parameter;

public class PanelTrainNNResultParameter {
	
	private int generation = 0;
	private double error = 0.0;
	
	public int getGeneration() {
		return this.generation;
	}
	
	public void setGeneration(int generation) {
		this.generation = generation;
	}
	
	public double getError() {
		return error;
	}
	
	public void setError(double error) {
		this.error = error;
	}

}
