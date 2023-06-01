package log.parsing.nn.train.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.ai.nn.EnnMonitorLogTrainCommonUtil;

public class TrainNNDataDictionary {
	
	private Map<Integer, Integer> inputOutputMap = new HashMap<Integer, Integer>();
	
	private int count = 0;
	private Map<String, Integer> keysMap = new HashMap<String, Integer>();
	private List<List<Integer>>  keysListList = new ArrayList<List<Integer>>();
	
	private List<String> resultsList = new ArrayList<String>();
	private Map<String, Integer> resultsPosMap = new HashMap<String, Integer>();
	
	public TrainNNDataDictionary() {
	}
	
	// 切词语需要改成跟formatInputList的一样
	public void addTrainData(String tag, String keys) {
		int i;
		String[] words = keys.split("\\s+");
		
		if (resultsPosMap.containsKey(tag) == false) {
			resultsList.add(tag);
			resultsPosMap.put(tag, resultsList.size() - 1);
		}
		
		keysListList.add(new ArrayList<Integer>());
		for (i = 0; i < words.length; ++i) {
			if (words[i].trim() == null || words[i].trim().equals("") == true) {
				continue;
			}
			
			if (keysMap.containsKey(words[i].toLowerCase()) == false) {
				keysMap.put(words[i].toLowerCase(), count++);
			}
			keysListList.get(keysListList.size() - 1).add(keysMap.get(words[i].toLowerCase()));
		}
		
		inputOutputMap.put(keysListList.size() - 1, resultsPosMap.get(tag));
	}
	
	public void clear() {
		inputOutputMap.clear();
		
		count = 0;
		keysMap.clear();
		keysListList.clear();
		
		resultsList.clear();
		resultsPosMap.clear();
	}
	
	public int getInputSize() {
		return keysMap.size();
	}
	
	public int getOutputSize() {
		return resultsList.size();
	}
	
	public List<Double> formatInputList(String data) throws Exception {
		int i;
		Integer pos;
		List<Double> inputList = new ArrayList<Double>();
		
		List<String> words = null;
		
		if (data == null) {
			return inputList;
		}
		
		for (i = 0; i < getInputSize(); ++i) {
			inputList.add(0.0);
		}
		
		words = EnnMonitorLogTrainCommonUtil.convertToWords(data);
		for (String word : words) {
			pos = keysMap.get(word.toLowerCase());
			if (pos != null) {
				inputList.set(pos, 1.0);
			}
		}
		
		return inputList;
	}
	
	public String getResults(int index) {
		if (index >= resultsList.size()) {
			return "null";
		}
		
		return resultsList.get(index);
	}
	
	public long getKeysPos(String key) {
		return keysMap.get(key);
	}
	
	public List<List<Double>> getInputListList() {
		int i, j;
		List<Integer> keysList = null;
		List<List<Double>> inputListList = new ArrayList<List<Double>>();
		
		for (i = 0; i < keysListList.size(); ++i) {
			keysList = keysListList.get(i);
			inputListList.add(new ArrayList<Double>());
			for (j = 0; j < getInputSize(); ++j) {
				inputListList.get(inputListList.size() - 1).add(0.0);
			}
			
			for (j = 0; j < keysList.size(); ++j) {
				inputListList.get(inputListList.size() - 1).set(keysList.get(j), 1.0);
			}
		}
		
		return inputListList;
	}
	
	public List<List<Double>> getOutputList() {
		int i, j;
		
		List<Double> outputList = null;
		List<List<Double>> outputListList = new ArrayList<List<Double>>();
		
		for (i = 0; i < keysListList.size(); ++i) {
			outputList = new ArrayList<Double>();
			for (j = 0; j < resultsList.size(); ++j) {
				outputList.add(0.0);
			}
			outputList.set(inputOutputMap.get(i), 1.0);
			outputListList.add(outputList);
		}
		
		return outputListList;
	}

}
