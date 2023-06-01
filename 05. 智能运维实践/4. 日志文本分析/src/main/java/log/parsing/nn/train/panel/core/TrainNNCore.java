package log.parsing.nn.train.panel.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import framework.ai.nn.NNFramework;
import framework.ai.nn.NNObject;
import framework.ai.nn.activation.NNActivationEnum;
import framework.ai.nn.weights.NNWeightEnum;
import log.parsing.nn.train.core.TrainNNData;
import log.parsing.nn.train.core.TrainNNDataDictionary;
import log.parsing.nn.train.panel.parameter.PanelTrainNNParameter;
import log.parsing.nn.train.panel.parameter.PanelTrainNNResultParameter;

public class TrainNNCore implements Runnable {
	
	private TrainNNFSM trainNNFSM = null;
	
	private NNObject nnObject = null;
	private List<List<Double>> inputListList = null;
	private List<List<Double>> targetOutputList = null;
	
	private PanelTrainNNParameter nnConfigureParameter = null;
	
	private boolean start = true;
	
	private TrainNNDataDictionary trainNNDataDictionary = null;
	
	protected Random random = new Random();
	protected int generation = 0;
	
	public TrainNNCore(TrainNNFSM trainNNFSM) {
		this.trainNNFSM = trainNNFSM;
	}

	public void train() {
		start = true;
		new Thread(this).start();
	}
	
	public NNObject getNnObject() {
		return nnObject;
	}

	public void setNnObject(NNObject nnObject) {
		this.nnObject = nnObject;
	}
	
	public List<List<Double>> getInputListList() {
		return inputListList;
	}

	public void setInputListList(List<List<Double>> inputListList) {
		this.inputListList = inputListList;
	}

	public List<List<Double>> getTargetOutputList() {
		return targetOutputList;
	}

	public void setTargetOutputList(List<List<Double>> targetOutputList) {
		this.targetOutputList = targetOutputList;
	}
	
	public TrainNNDataDictionary getTrainNNData() {
		return trainNNDataDictionary;
	}

	public void setTrainNNData(TrainNNDataDictionary trainNNData) {
		this.trainNNDataDictionary = trainNNData;
	}

	public void setTrainNNParameter(PanelTrainNNParameter nnConfigureParameter) {
		this.nnConfigureParameter = nnConfigureParameter;
	}
	
	public PanelTrainNNParameter getNNConfigureParameter() {
		return nnConfigureParameter;
	}
	
	public void stop() {
		start = false;
	}

	@Override
	public void run() {
		generation = 0;
		while (start) {
			try {
				++generation;
				training();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void training() throws Exception {
		int i, j;
		List<Double> inputList = null;
		
		nnObject.clearError();
		for (i = 0; i < inputListList.size(); ++i) {
			inputList = new ArrayList<Double>(inputListList.get(i));
			// 添加噪声
			for (j = 0; j < inputList.size(); ++j) {
				inputList.set(j, inputList.get(j) + nnObject.getMaxNoise() * (random.nextDouble() - random.nextDouble()));
			}
			NNFramework.compute(nnObject, inputList, targetOutputList.get(i), NNActivationEnum.Sigmoid, NNWeightEnum.Adagrad, 0, 0);
		}
		
		PanelTrainNNResultParameter trainNNInfoParameter = new PanelTrainNNResultParameter();
		trainNNInfoParameter.setGeneration(generation);
		trainNNInfoParameter.setError(nnObject.getError());
		TrainNNData.setNNObject(nnObject.cloneNNObject());
		TrainNNData.setTrainNNDataDictionary(trainNNDataDictionary);
		trainNNFSM.addEvent(new TrainNNEvent(TrainNNEventEnum.TrainInfo, trainNNInfoParameter));
	}
	
//	private boolean training() {
//		int i;
//		
//		EnnMonitorLogTrainJob.Builder trainJob = null;
//		
//		TrainNNResultParameter trainNNResultParameter = null;
//		
//		List<EnnMonitorLogTrainJobStatus> jobStatusList = null;
//		EnnMonitorLogTrainJobGeStatusRsp jobStatusRsp = null;
//		
//		EnnMonitorLogTrainJogGetBestJobRsp getBestJobRsp = null;
//		
//		logger.info("start Train");
//		
//		if (nnObject != null && inputListList != null && targetOutputList != null) {
//			try {
//				// deploy job
//				trainJob = EnnMonitorLogTrainJob.newBuilder();
//				trainJob.setJobId(1000l);
//				trainJob.setJob(gson.toJson(nnObject));
//				trainJob.setGenomNum(320);
//				trainJob.setChangeParameter(50);
//				trainJob.setEliteGenerator(50);
//				
//				for (i = 0; i < inputListList.size(); ++i) {
//					trainJob.addInput(EnnMonitorLogTrainJobInput.newBuilder().addAllInput(inputListList.get(i)).build());
//				}
//				for (i = 0; i < targetOutputList.size(); ++i) {
//					trainJob.addOutput(EnnMonitorLogTrainJobOutput.newBuilder().addAllOutput(targetOutputList.get(i)).build());
//				}
//				
//				logger.info("deployJob");
//				EnnMonitorLogTrainMasterClientUtil.getEnnMonitorLogTrainMasterClient().deployJob(
//						EnnMonitorLogTrainJobDeployReq.newBuilder().setJob(trainJob.build()).build());
//				
//				// 获取状态
//				while (start) {
//					try {
//						logger.debug("get jobStatus");
//						jobStatusRsp = EnnMonitorLogTrainMasterClientUtil.getEnnMonitorLogTrainMasterClient().getJobStatus(EnnMonitorLogTrainJobGeStatusReq.newBuilder().setJobId(1000l).build());
//						if (jobStatusRsp.getIsSuccess() == true) {
//							jobStatusList = jobStatusRsp.getJobStatusList();
//							
//							trainNNResultParameter = new TrainNNResultParameter();
//							trainNNResultParameter.setJobStatusList(jobStatusList);
//							trainNNFSM.addEvent(new TrainNNEvent(TrainNNEventEnum.UpdateResult, trainNNResultParameter));
//							
//							for (i = 0; i < jobStatusList.size(); ++i) {
//								if (jobStatusList.get(i).getBestGeneration() <= 0) {
//									continue;
//								}
//								if (jobStatusList.get(i).getBestError() <= nnConfigureParameter.getDeviation()) {
//									logger.info("training completed");
//									break;
//								}
//							}
//							if (i != jobStatusList.size()) {
//								break;
//							}
//						} else {
//							logger.error(jobStatusRsp.getError());
//						}
//					} catch (Exception e) {
//						logger.error(e.getMessage(), e);
//					} finally {
//						try {
//							Thread.sleep(3000);
//						} catch (Exception e1) {
//							logger.error(e1.getMessage(), e1);
//						}
//					}
//				}
//			} catch (Exception e) {
//				logger.error(e.getMessage(), e);
//				return false;
//			}
//		} else {
//			return false;
//		}
//		
//		if (jobStatusList != null) {
//			getBestJobRsp = EnnMonitorLogTrainMasterClientUtil.getEnnMonitorLogTrainMasterClient().getBestJob(EnnMonitorLogTrainJogGetBestJobReq.newBuilder().setJobId(1000l).build());
//			if (getBestJobRsp.getIsSuccess() == true) {
//				CoreContextUtil.setNnObject(gson.fromJson(getBestJobRsp.getBestJob(), NNObject.class));
//				CoreContextUtil.setTrainNNData(trainNNData);
//			} else {
//				CoreContextUtil.setNnObject(null);
//				CoreContextUtil.setTrainNNData(null);
//			}
//		} else {
//			CoreContextUtil.setNnObject(null);
//			CoreContextUtil.setTrainNNData(null);
//		}
//		
//		EnnMonitorLogTrainMasterClientUtil.getEnnMonitorLogTrainMasterClient().ctlJob(
//				EnnMonitorLogTrainJobCtlReq.newBuilder().setCtl(EnnMonitorLogTrainJobCtlEnum.Stop).setJobId(1000l).build());
//		
//		return true;
//	}

}
