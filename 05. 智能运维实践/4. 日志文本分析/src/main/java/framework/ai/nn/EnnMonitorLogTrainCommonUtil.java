package framework.ai.nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class EnnMonitorLogTrainCommonUtil {
	
	public static List<String> convertToWords(String log) throws Exception {
		List<String> words = new ArrayList<String>();
		
		log = log.toLowerCase();
		log = log.replaceAll("(\\(|\\)|\\.|-|\\[|\\]|:)", " ");
		String[] logItems = log.split("(\\s|/)");
		Arrays.sort(logItems);
		
		for (String logItem : logItems) {
			if (StringUtils.isNumericSpace(logItem) == true) {
				continue;
			}
			words.add(logItem);
		}
		
		return words;
	}

}
