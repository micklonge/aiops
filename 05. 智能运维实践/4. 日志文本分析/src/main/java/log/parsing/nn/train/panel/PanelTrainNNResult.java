package log.parsing.nn.train.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import framework.ai.nn.NNFramework;
import framework.ai.nn.NNFramework.NNComputeResult;
import log.parsing.nn.train.core.TrainNNData;
import log.parsing.nn.train.panel.core.TrainNNFSM;

public class PanelTrainNNResult extends JPanel {

	private static final long serialVersionUID = -1396329941639001597L;
	
	private final String columnHeader[] = {"Id", "DateTime", "LogLevel", "Log", "Tag", "Match"};
	private JTable table = new JTable(new DefaultTableModel());
	private JScrollPane scrollPane = new JScrollPane(table);
	
	public PanelTrainNNResult(TrainNNFSM trainNNFSM, int labelWidth, int textWidth) {
		TableColumn column = null;
		DefaultTableModel defaultTableModel = null;
		
		SpringLayout tspLayout = new SpringLayout();
		setLayout(tspLayout);
		
		// -----------------------------------------------------------------------------------------------------
		add(scrollPane);
		tspLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		tspLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		tspLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, this);
		
		defaultTableModel = (DefaultTableModel) table.getModel();
		defaultTableModel.setColumnIdentifiers(columnHeader);
		
		column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(50);
		column.setMinWidth(50);
		column.setMaxWidth(50);
		
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(200);
		column.setMinWidth(200);
		column.setMaxWidth(200);
		
		column = table.getColumnModel().getColumn(2);
		column.setPreferredWidth(50);
		column.setMinWidth(50);
		column.setMaxWidth(50);
		
		column = table.getColumnModel().getColumn(4);
		column.setPreferredWidth(100);
		column.setMinWidth(100);
		column.setMaxWidth(100);
		
		column = table.getColumnModel().getColumn(5);
		column.setPreferredWidth(100);
		column.setMinWidth(100);
		column.setMaxWidth(100);
	}
	
	public void computeResult() throws Exception {
		Vector<String> rowData = null;
		DefaultTableModel defaultTableModel = null;
		
		defaultTableModel = (DefaultTableModel) table.getModel();
		defaultTableModel.getDataVector().clear();
		
		int count = 1;
		for (Map<String, String> map : TrainNNData.getNormalLogList()) {
			rowData = new Vector<String>();
			
			NNComputeResult nnComputeResult = NNFramework.computeWithTag(map.get("log"));
			
			rowData.add("" + count);
			rowData.add(map.get("dateTime"));
			rowData.add(map.get("logLevel"));
			rowData.add(map.get("log"));
			rowData.add(nnComputeResult.getTag());
			rowData.add("" + nnComputeResult.getMatch());
			
			defaultTableModel.getDataVector().add(rowData);
			++count;
		}

		table.updateUI();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(new Color(248, 250, 241));
	}
}
