package log.parsing.nn.train.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.ai.nn.NNObject;

public class TrainNNData {

	private static List<String> originLogList = null;
	private static List<Map<String, String>> normalLogList = null;
	private static Map<String, String> templateMap = null;
	
	private static TrainNNDataDictionary trainNNDataDictionary = null;
	private static NNObject nnObject = null;
	
	public static void setOriginLogList(List<String> datas) {
		originLogList = datas;
	}
	
	public static List<String> getOriginLogList() {
		return originLogList;
	}
	
	public static void setNormalLogList(List<Map<String, String>> datas) {
		normalLogList = datas;
	}
	
	public static List<Map<String, String>> getNormalLogList() {
		return normalLogList;
	}
	
	public static void setTemplateList(List<String> datas) {
		templateMap = new HashMap<String, String>();
		for (String data : datas) {
			templateMap.put(data, "Normal");
		}
	}
	
	public static Map<String, String> getTemplateMap() {
		return templateMap;
	}
	
	public static void addTemplateTag(String template, String tag) {
		if (tag == null || tag.trim().equals("") == true) {
			tag = "Normal";
		}
		
		templateMap.put(template, tag);
	}
	
	public static void setNNObject(NNObject object) {
		nnObject = object;
	}
	
	public static NNObject getNNObject() {
		return nnObject;
	}
	
	public static void setTrainNNDataDictionary(TrainNNDataDictionary data) {
		trainNNDataDictionary = data;
	}
	
	public static TrainNNDataDictionary getTrainNNDataDictionary() {
		return trainNNDataDictionary;
	}
	
}
