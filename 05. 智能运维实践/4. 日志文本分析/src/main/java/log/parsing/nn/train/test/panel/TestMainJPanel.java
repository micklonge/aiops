package log.parsing.nn.train.test.panel;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class TestMainJPanel extends JPanel {

	private static final long serialVersionUID = -3212620382188255863L;
	
	private TestRunJPanel testRunJPanel = null;
	
	public TestMainJPanel() {
		SpringLayout tspLayout = new SpringLayout();
		setLayout(tspLayout);
		
		testRunJPanel = new TestRunJPanel();
		add(testRunJPanel);
		tspLayout.putConstraint(SpringLayout.NORTH, testRunJPanel, 0, SpringLayout.NORTH, this);
		tspLayout.putConstraint(SpringLayout.SOUTH, testRunJPanel, 0, SpringLayout.SOUTH, this);
		tspLayout.putConstraint(SpringLayout.WEST, testRunJPanel, 0, SpringLayout.WEST, this);
		tspLayout.putConstraint(SpringLayout.EAST, testRunJPanel, 0, SpringLayout.EAST, this);
	}
	
}
