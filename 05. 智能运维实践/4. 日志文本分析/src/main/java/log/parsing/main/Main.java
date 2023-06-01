package log.parsing.main;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private static final long serialVersionUID = -8008221899785868824L;

	public static void main(String[] args) throws Exception {
	    
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1800, 1200);
		
		setContentPane(new PanelLogParsingTrainMain());
	}
	
}
