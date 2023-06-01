package log.parsing.preprocess.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import framework.ai.nn.EnnMonitorLogTrainCommonUtil;

public class LogParsingTemplate {
	
	private static Set<String> keyWords = new HashSet<String>();
	private static Set<String> filterWords = new HashSet<String>();
	
	static {
		//filterWords.add();
	}
	
	private static Set<String> getTemplateFromLog(List<String> logs) throws Exception {
		String template = null;
		Set<String> templates = new HashSet<String>();
		
		for (String log : logs) {
			List<String> logItems = EnnMonitorLogTrainCommonUtil.convertToWords(log);
			
			template = null;
			for (String logItem : logItems) {
				if (template == null) {
					template = logItem;
				} else {
					template = template + " " + logItem;
				}
			}
			templates.add(template);
		}
		
		return templates;
	}
	
	public static Set<String> run(List<String> logs) throws Exception {
		Set<String> templates = getTemplateFromLog(logs);
		
		for (String template : templates) {
			System.out.println(template);
		}
		
		return templates;
	}

}
