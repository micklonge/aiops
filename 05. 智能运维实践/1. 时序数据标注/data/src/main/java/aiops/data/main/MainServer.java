package aiops.data.main;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

public class MainServer extends JFrame {
	
	private static final long serialVersionUID = -8008221899785868824L;

	public static void main(String[] args) throws Exception {
		long start = 1682229540;
		
		String fileName = "demo.csv";
        String[] titleRow = {"timestamp", "value", "label"};

        ArrayList<String[]> dataRows = new ArrayList<>();
		for (double i = 0; i < Math.PI * 4; i = i + Math.PI / 10, start += 60) {
			System.out.println(Math.sin(i) * 100 + 100);
			String[] dataRow = {String.valueOf(start), String.valueOf(Math.sin(i) * 100 + 100), "0"};
			dataRows.add(dataRow);
		}
		
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), Charset.forName("UTF-8"));
        CSVWriter csvWriter = (CSVWriter) new CSVWriterBuilder(writer).build();
        csvWriter.writeNext(titleRow, false);
        csvWriter.writeAll(dataRows, false);
        csvWriter.close();		
	}

}
