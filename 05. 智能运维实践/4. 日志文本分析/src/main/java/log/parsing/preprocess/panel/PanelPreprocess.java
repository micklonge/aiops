package log.parsing.preprocess.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import log.parsing.nn.train.core.TrainNNData;
import log.parsing.preprocess.core.LogParsingInput;
import log.parsing.preprocess.core.LogParsingNormalizingTask;
import log.parsing.preprocess.core.LogParsingTemplate;

public class PanelPreprocess extends JPanel {

	private static final long serialVersionUID = 299839299523464428L;
	
	// origin log
	private final String originLogColumnHeader[] = {"Id", "Log"};
	private JTable originLogTable = new JTable(new DefaultTableModel());
	private JScrollPane originLogScrollPane = new JScrollPane(originLogTable);
	
	private final String normalLogColumnHeader[] = {"Id", "DateTime", "LogLevel", "Log"};
	private JTable normalLogTable = new JTable(new DefaultTableModel());
	private JScrollPane normalLogScrollPane = new JScrollPane(normalLogTable);
	
	private final String templateColumnHeader[] = {"Id", "Template", "tag"};
	private JTable templateTable = new JTable(new DefaultTableModel());
	private JScrollPane templateScrollPane = new JScrollPane(templateTable);
	private JButton updateButton = new JButton();
	
	public PanelPreprocess() throws Exception {		
		SpringLayout tspLayout = new SpringLayout();
		setLayout(tspLayout);

		// -----------------------------------------------------------------------------------------------------------
		add(originLogScrollPane);
		tspLayout.putConstraint(SpringLayout.NORTH, originLogScrollPane, 10, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, originLogScrollPane, 10, SpringLayout.WEST, this);
		tspLayout.putConstraint(SpringLayout.EAST, originLogScrollPane, 600, SpringLayout.WEST, originLogScrollPane);
		tspLayout.putConstraint(SpringLayout.SOUTH, originLogScrollPane, -10, SpringLayout.SOUTH, this);
		displayOriginLog();
		
		add(normalLogScrollPane);
		tspLayout.putConstraint(SpringLayout.NORTH, normalLogScrollPane, 10, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, normalLogScrollPane, 10, SpringLayout.EAST, originLogScrollPane);
		tspLayout.putConstraint(SpringLayout.EAST, normalLogScrollPane, 600, SpringLayout.WEST, normalLogScrollPane);
		tspLayout.putConstraint(SpringLayout.SOUTH, normalLogScrollPane, -10, SpringLayout.SOUTH, this);
		displayNormalLog();
		
		add(templateScrollPane);
		tspLayout.putConstraint(SpringLayout.NORTH, templateScrollPane, 10, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, templateScrollPane, 10, SpringLayout.EAST, normalLogScrollPane);
		tspLayout.putConstraint(SpringLayout.EAST, templateScrollPane, -10, SpringLayout.EAST, this);
		tspLayout.putConstraint(SpringLayout.SOUTH, templateScrollPane, 393, SpringLayout.NORTH, templateScrollPane);
		displayTemplate();
		
		updateButton.setText("Update Template");
		add(updateButton);
		tspLayout.putConstraint(SpringLayout.NORTH, updateButton, 10, SpringLayout.SOUTH, templateScrollPane);
		tspLayout.putConstraint(SpringLayout.WEST, updateButton, 10, SpringLayout.EAST, normalLogScrollPane);
		tspLayout.putConstraint(SpringLayout.EAST, updateButton, -10, SpringLayout.EAST, this);
//		tspLayout.putConstraint(SpringLayout.SOUTH, updateButton, -10, SpringLayout.SOUTH, this);
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel defaultTableModel = (DefaultTableModel) templateTable.getModel();
				for (int i = 0; i < defaultTableModel.getDataVector().size(); ++i) {
					Vector<String> data = (Vector<String>) defaultTableModel.getDataVector().get(i);
					TrainNNData.addTemplateTag(data.get(1), data.get(2));
				}
			}
			
		});
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(new Color(248, 250, 241));
	}
	
	private void displayTemplate() throws Exception {
		TableColumn column = null;
		DefaultTableModel defaultTableModel = null;
		
		defaultTableModel = (DefaultTableModel) templateTable.getModel();
		defaultTableModel.setColumnIdentifiers(templateColumnHeader);
		
		column = templateTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(50);
		column.setMinWidth(50);
		column.setMaxWidth(50);
		
		column = templateTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(350);
		column.setMinWidth(350);
		column.setMaxWidth(350);
		
		List<String> logs = new ArrayList<String>();
		for (Map<String, String> logItemMap : TrainNNData.getNormalLogList()) {
			logs.add(logItemMap.get("log"));
		}
		TrainNNData.setTemplateList(new ArrayList<String>(LogParsingTemplate.run(logs)));
		
		// log input
		Vector<String> rowData = null;
		int count = 1;
		for (Map.Entry<String, String> entry : TrainNNData.getTemplateMap().entrySet()) {
			rowData = new Vector<String>();
			
			rowData.add("" + count);
			rowData.add(entry.getKey());
			rowData.add(entry.getValue());
			
			defaultTableModel.getDataVector().add(rowData);
			++count;
		}
		templateTable.updateUI();
	}
	
	private void displayNormalLog() throws Exception {
		TableColumn column = null;
		DefaultTableModel defaultTableModel = null;
		
		defaultTableModel = (DefaultTableModel) normalLogTable.getModel();
		defaultTableModel.setColumnIdentifiers(normalLogColumnHeader);
		
		column = normalLogTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(50);
		column.setMinWidth(50);
		column.setMaxWidth(50);
		
		column = normalLogTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(150);
		column.setMinWidth(150);
		column.setMaxWidth(150);
		
		column = normalLogTable.getColumnModel().getColumn(2);
		column.setPreferredWidth(50);
		column.setMinWidth(50);
		column.setMaxWidth(50);
		
		TrainNNData.setNormalLogList(LogParsingNormalizingTask.run(TrainNNData.getOriginLogList()));
		
		// log input
		Vector<String> rowData = null;
		for (int i = 0; i < TrainNNData.getNormalLogList().size(); ++i) {
			rowData = new Vector<String>();
			
			rowData.add("" + (i + 1));
			rowData.add(TrainNNData.getNormalLogList().get(i).get("dateTime"));
			rowData.add(TrainNNData.getNormalLogList().get(i).get("logLevel"));
			rowData.add(TrainNNData.getNormalLogList().get(i).get("log"));
			
			defaultTableModel.getDataVector().add(rowData);
		}
		normalLogTable.updateUI();
	}
	
	private void displayOriginLog() throws Exception {
		TableColumn column = null;
		DefaultTableModel defaultTableModel = null;
		
		defaultTableModel = (DefaultTableModel) originLogTable.getModel();
		defaultTableModel.setColumnIdentifiers(originLogColumnHeader);
		
		column = originLogTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(50);
		column.setMinWidth(50);
		column.setMaxWidth(50);
		
		// log input
		Vector<String> rowData = null;
		TrainNNData.setOriginLogList(LogParsingInput.run());
		for (int i = 0; i < TrainNNData.getOriginLogList().size(); ++i) {
			rowData = new Vector<String>();
			
			rowData.add("" + (i + 1));
			rowData.add("" + TrainNNData.getOriginLogList().get(i));
			
			defaultTableModel.getDataVector().add(rowData);
		}
		originLogTable.updateUI();
	}

}
