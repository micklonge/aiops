package log.parsing.preprocess.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LogParsingInput {
	
	public static List<String> run() throws Exception {
		List<String> logList = new ArrayList<String>();
		
		File file = new File("Apache.log");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String log = null;
            while ((log = reader.readLine()) != null) {
            	logList.add(log);
            }
            reader.close();
        } finally {
            if (reader != null) {
            	reader.close();
            }
        }
        
        return logList;
	}

}
