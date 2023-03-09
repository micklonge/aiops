package enn.monitor.ai.ga.tsp.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import enn.monitor.ai.ga.main.TSPGAThread;
import enn.monitor.ai.ga.tsp.TSPEvent;
import enn.monitor.ai.ga.tsp.TSPEventEnum;
import enn.monitor.ai.ga.tsp.TSPParameter;
import enn.monitor.ai.ga.tsp.TSPResult;

public class TSPJPanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 2002012308498717087L;
	
	private static final int labelWidth = 120;
	private static final int textWidth = 150;
	
	private TSPParameterJPanel parameterJPanel = null;
	private TSPDiaplayJPanel diaplayJPanel = null;
	private TSPResultJPanel resultJPanel = null;
	private TSPCircleJPanel circleJPanel = null;
	
	private JButton button = null;
	private JButton refreshButton = new JButton();
	
	private boolean isRunning = false;

	private BlockingQueue<TSPEvent> tspEventQueue = new LinkedBlockingQueue<TSPEvent>();
	
	public TSPJPanel() {
		TSPParameter tspParameter = null;
		
		SpringLayout tspLayout = new SpringLayout();
		setLayout(tspLayout);
		
		parameterJPanel = new TSPParameterJPanel(labelWidth, textWidth);
		add(parameterJPanel);
		tspLayout.putConstraint(SpringLayout.NORTH, parameterJPanel, 10, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, parameterJPanel, 10, SpringLayout.WEST, this);
		tspLayout.putConstraint(SpringLayout.EAST, parameterJPanel, 300, SpringLayout.WEST, parameterJPanel);
		tspLayout.putConstraint(SpringLayout.SOUTH, parameterJPanel, 420, SpringLayout.NORTH, parameterJPanel);
		
		diaplayJPanel = new TSPDiaplayJPanel(labelWidth, textWidth);
		add(diaplayJPanel);
		tspLayout.putConstraint(SpringLayout.NORTH, diaplayJPanel, 10, SpringLayout.SOUTH, parameterJPanel);
		tspLayout.putConstraint(SpringLayout.WEST, diaplayJPanel, 10, SpringLayout.WEST, this);
		tspLayout.putConstraint(SpringLayout.EAST, diaplayJPanel, 300, SpringLayout.WEST, diaplayJPanel);
		tspLayout.putConstraint(SpringLayout.SOUTH, diaplayJPanel, 420, SpringLayout.NORTH, diaplayJPanel);
		
		tspParameter = parameterJPanel.getTSPParameter();
		diaplayJPanel.setTSPParameter(tspParameter);
		
		resultJPanel = new TSPResultJPanel(labelWidth, textWidth);
		add(resultJPanel);
		tspLayout.putConstraint(SpringLayout.NORTH, resultJPanel, 10, SpringLayout.SOUTH, diaplayJPanel);
		tspLayout.putConstraint(SpringLayout.WEST, resultJPanel, 10, SpringLayout.WEST, this);
		tspLayout.putConstraint(SpringLayout.EAST, resultJPanel, 300, SpringLayout.WEST, resultJPanel);
		tspLayout.putConstraint(SpringLayout.SOUTH, resultJPanel, 200, SpringLayout.NORTH, resultJPanel);
		
		button = new JButton();
		button.setText("开始");
		add(button);
		tspLayout.putConstraint(SpringLayout.SOUTH, button, -10, SpringLayout.SOUTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, button, 10, SpringLayout.WEST, this);
		
		refreshButton.setText("刷新基本时间");
		add(refreshButton);
		tspLayout.putConstraint(SpringLayout.SOUTH, refreshButton, -10, SpringLayout.SOUTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, refreshButton, 10, SpringLayout.EAST, button);
		
		circleJPanel = new TSPCircleJPanel(tspParameter.getCities());
		add(circleJPanel);
		tspLayout.putConstraint(SpringLayout.NORTH, circleJPanel, 10, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.SOUTH, circleJPanel, -10, SpringLayout.SOUTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, circleJPanel, 10, SpringLayout.EAST, parameterJPanel);
		tspLayout.putConstraint(SpringLayout.EAST, circleJPanel, -10, SpringLayout.EAST, this);
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TSPEvent tspEvent = null;
				
				if (isRunning == true) {
					button.setText("开始");
					isRunning = false;
					
					tspEvent = new TSPEvent();
					tspEvent.setTspEventEnum(TSPEventEnum.Stop);
					tspEventQueue.add(tspEvent);
				} else {
					button.setText("暂停");
					isRunning = true;
					
					tspEvent = new TSPEvent();
					tspEvent.setTspEventEnum(TSPEventEnum.Start);
					tspEventQueue.add(tspEvent);
				}
			}
			
		});
		
		refreshButton.addActionListener(new ActionListener() {
			TSPEvent tspEvent = new TSPEvent();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tspEvent.setTspEventEnum(TSPEventEnum.ResetStartTime);
				tspEventQueue.add(tspEvent);
			}
			
		});
		
		new Thread(this).start();
	}

	@Override
	public void run() {
		TSPParameter tspParameter = null;
		
		TSPEvent tspEvent = null;
		TSPGAThread tspGAThread = null;
		
		TSPResult tspResult = null;

		try {
			while (true) {
				tspEvent = tspEventQueue.poll(100, TimeUnit.MICROSECONDS);
				
				if (tspEvent == null) {
					continue;
				}
				
				switch (tspEvent.getTspEventEnum()) {
				case Start:

					button.setText("暂停");
					isRunning = true;
					
					tspParameter = parameterJPanel.getTSPParameter();
					diaplayJPanel.setTSPParameter(tspParameter);
					
					circleJPanel.init(tspParameter.getCities());
					tspParameter.setPositionList(circleJPanel.getPositionList());
					
					tspGAThread = new TSPGAThread(tspParameter, tspEventQueue);
					tspGAThread.startThread();
					break;
				case Stop:
					button.setText("开始");
					isRunning = false;
					
					tspGAThread.stopThead();
					break;
				case Update:
					tspResult = (TSPResult) tspEvent.getData();
					
					resultJPanel.setTSPResult(tspResult);
					circleJPanel.drawRoute(tspResult.getCities());
					break;
				case ResetStartTime:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
