package log.parsing.preprocess.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import log.normalization.aggregator.LangFormatAggregatorFSM;
import log.normalization.core.LangFormatRegexFSM;

public class LogParsingNormalizingTask {
	
	private static void logPrint(String log, Map<String, String> result) {
		if (result == null) {
			return;
		}
		
		System.out.println(log);
		for (Map.Entry<String, String> entry : result.entrySet()) {
			System.out.println("Key: " + entry.getKey() + " ------ Value: " + entry.getValue());
		}
		
		System.out.println();
	}
	
	private static Map<String, String> logNormalizing(String log) throws Exception {
		Map<String, String> resultMap = null;
		
		LangFormatRegexFSM regexFSM = new LangFormatRegexFSM();
		LangFormatAggregatorFSM aggregatorFSM = new LangFormatAggregatorFSM();
		
		regexFSM.parse("^\\[\\w*\\s*(?<month>\\w+)\\s*(?<day>\\d+)\\s*(?<time>[^\\s]+)\\s*(?<year>\\d+)\\]\\s*\\[(?<logLevel>\\w+)\\]\\s*(?<log>.*)$");
		resultMap = regexFSM.match(log);
		aggregatorFSM.parse("<dateTime>%year%-%month[Dec:12]%-%day% %time%,%logLevel[notice:INFO/error:ERROR]%,%log%");
		resultMap = aggregatorFSM.aggregator(resultMap);
		
		return resultMap;
	}
	
	public static List<Map<String, String>> run(List<String> logList) throws Exception {
		List<Map<String, String>> logMapList = new ArrayList<Map<String, String>>();
		
		for (String log : logList) {
			Map<String, String> logItemMap = logNormalizing(log);
			logMapList.add(logItemMap);
			logPrint(log, logItemMap);
		}
        
        return logMapList;
	}
	
}
