package log.normalization.test;

import java.util.Map;

import junit.framework.TestCase;
import log.normalization.aggregator.LangFormatAggregatorFSM;
import log.normalization.core.LangFormatRegexFSM;

public class LogFormatTest extends TestCase {
	
	public void test0() throws Exception {
		String log = "2017-06-14 16:39:04.229  INFO 26069 --- [onPool-worker-1] c.g.filters.post.RequestAuditFilter      : [topic=console-audit] RequestAuditMessage=RequestAuditMessage{service=GATEWAY, requestId='1be6e1c5-2857-426d-a308-2cbf814dc87e', userId='null', namespaceName='null', url='https://localhost:8809/gw/as/api/v1', httpMethod='GET', httpStatus=200, clientIp='127.0.0.1', startTime=Wed Jun 14 16:39:04 CST 2017, elapsed=80, extras='{}'}";
		Map<String, String> resultMap = null;
		
		LangFormatRegexFSM regexFSM = new LangFormatRegexFSM();
		LangFormatAggregatorFSM aggregatorFSM = new LangFormatAggregatorFSM();
		
		regexFSM.parse("^(?<dateTime>[\\d\\-]+[\\s]*[\\d\\:\\.]+)[\\s]*(?<logLevel>[\\w]+)[\\s]*(?<log>.*)$");
		resultMap = regexFSM.match(log);
		aggregatorFSM.parse("%dateTime%,%logLevel%,%log%");
		resultMap = aggregatorFSM.aggregator(resultMap);
		logPrint(log, resultMap);
	}
	
	public void test1() throws Exception {
		String log = "2017-07-16T15:55:48.059+0800 I NETWORK  [conn13272] received client metadata from 172.5.240.8:37262 conn13272: { application: { name: \"MongoDB Shell\" }, driver: { name: \"MongoDB Internal Client\", version: \"3.4.6\" }, os: { type: \"Linux\", name: \"Ubuntu\", architecture: \"x86_64\", version: \"16.04\" } }\n";
		Map<String, String> resultMap = null;
		
		LangFormatRegexFSM regexFSM = new LangFormatRegexFSM();
		LangFormatAggregatorFSM aggregatorFSM = new LangFormatAggregatorFSM();
		
		regexFSM.parse("^(?<date>[\\d\\-]+)T(?<time>[\\d\\:\\.]+)[^\\s]*[\\s]*(?<logLevel>[^\\s]+)[\\s]*(?<log>.*)$");
		resultMap = regexFSM.match(log);
		aggregatorFSM.parse("%logLevel[I:INFO/W:WARN/E:ERROR/F:FATAL]%,%log%,<dateTime>%date% %time%");
		resultMap = aggregatorFSM.aggregator(resultMap);
		logPrint(log, resultMap);
	}
	
	public void test2() throws Exception {
		String log = "INFO 26069 --- [onPool-worker-1] c.g.filters.post.RequestAuditFilter      : RequestAuditMessage=RequestAuditMessage{service=GATEWAY, requestId='1be6e1c5-2857-426d-a308-2cbf814dc87e', userId='null', namespaceName='null', url='https://localhost:8809/gw/as/api/v1', httpMethod='GET', httpStatus=200, clientIp='127.0.0.1', startTime=Wed Jun 14 16:39:04 CST 2017, elapsed=80, extras='{}'}";
		Map<String, String> resultMap = null;
		
		LangFormatRegexFSM regexFSM = new LangFormatRegexFSM();
		LangFormatAggregatorFSM aggregatorFSM = new LangFormatAggregatorFSM();
		
		regexFSM.parse("^(?<logLevel>[\\w]+)[\\s]*(?<log>.*)$");
		resultMap = regexFSM.match(log);
		aggregatorFSM.parse("<dateTime>$datetime$,%logLevel%,%log%");
		resultMap = aggregatorFSM.aggregator(resultMap);
		logPrint(log, resultMap);
	}
	
	public void test3() throws Exception {
		String log = "I0718 17:07:47.529308     758 server.go:770] Started logformatlet v1.5.1-148+69ea644b53bc73-dirty";
		Map<String, String> resultMap = null;
		
		LangFormatRegexFSM regexFSM = new LangFormatRegexFSM();
		LangFormatAggregatorFSM aggregatorFSM = new LangFormatAggregatorFSM();
		
		regexFSM.parse("^(?<logLevel>[\\w])(?<month>\\d\\d)(?<day>\\d\\d)[\\s]*(?<time>[^\\s]*)[\\s]*(?<log>.*)$");
		resultMap = regexFSM.match(log);
		aggregatorFSM.parse("%logLevel[I:INFO/W:WARN/E:ERROR/F:FATAL]%,%log%,<dateTime>$year$-%month%-%day% %time%");
		resultMap = aggregatorFSM.aggregator(resultMap);
		logPrint(log, resultMap);
	}
	
	public void test4() throws Exception {
		String log = "18/07/12 15:46:01 INFO BlockManager: Removing RDD 6627";
		Map<String, String> resultMap = null;
		
		LangFormatRegexFSM regexFSM = new LangFormatRegexFSM();
		LangFormatAggregatorFSM aggregatorFSM = new LangFormatAggregatorFSM();
		
		regexFSM.parse("^(?<year>\\d\\d)/(?<month>\\d\\d)/(?<day>\\d\\d)[\\s]*(?<time>[^\\s]*)[\\s]*(?<logLevel>[\\w]*)[\\s]*(?<log>.*)$");
		resultMap = regexFSM.match(log);
		aggregatorFSM.parse("<datetime>20%year%-%month%-%day% %time%,%log%,%logLevel%");
		resultMap = aggregatorFSM.aggregator(resultMap);
		logPrint(log, resultMap);
	}
	
	private void logPrint(String log, Map<String, String> result) {
		if (result == null) {
			return;
		}
		
		System.out.println(log);
		System.out.println();
		for (Map.Entry<String, String> entry : result.entrySet()) {
			System.out.println("Key: " + entry.getKey() + " ------ Value: " + entry.getValue());
		}
	}

}
