import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class GameFrame extends JFrame {
	
	private JSlider speedSlider;
	private JTextField asteroidAdder;
	private JCheckBox canDieBox;
	private DisplayPanel gamePanel;
	
	public GameFrame(){
		this.setTitle("Asteroids");
		this.setSize(800, 800);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(800,800));
		mainPanel.setLayout(new BorderLayout());
		
		JPanel gameSettingPanel = new JPanel();
		gameSettingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel speed = createLabel("Speed");
		speedSlider = new JSlider(1,5,1);
		speedSlider.setMajorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintTrack(true);
		speedSlider.setPaintLabels(true);
		gameSettingPanel.add(speed);
		gameSettingPanel.add(speedSlider);
		JLabel addAsterL = createLabel("Add Asteroids (-1>)");
		asteroidAdder = new JTextField(15);
		gameSettingPanel.add(addAsterL);
		gameSettingPanel.add(asteroidAdder);
		JLabel dieL = createLabel("Can Die");
		canDieBox = new JCheckBox();
		canDieBox.setSelected(true);
		gameSettingPanel.add(dieL);
		gameSettingPanel.add(canDieBox);
		JButton updateB = createButton("Update");
		updateB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gamePanel.updateGameSettings(canDieBox.isSelected(),speedSlider.getValue(),asteroidAdder.getText());
				reset();
			}
			
		});
		
		gameSettingPanel.add(updateB);
		mainPanel.add(gameSettingPanel,BorderLayout.SOUTH);
		
		gamePanel = new DisplayPanel();	
		mainPanel.add(gamePanel,BorderLayout.CENTER);
		this.getContentPane().add(mainPanel);
		this.pack();
		
	}
	
	/**
	 * This method will create a typical JLabel object.
	 */
	public JLabel createLabel(String label){
		JLabel rtnL = new JLabel(label);
		rtnL.setFont(new Font("Arial",Font.BOLD,12));
		return rtnL;
	}
	
	/**
	 * This method will create a typical JButton
	 */
	public JButton createButton(String label){
		JButton rtnB = new JButton(label);
		rtnB.setHorizontalAlignment(SwingConstants.RIGHT);
//		rtnB.setMaximumSize(new Dimension(200,20));
//		rtnB.setMinimumSize(new Dimension(200,20));
//		rtnB.setPreferredSize(new Dimension(200,20));
		return rtnB;
	}
	
	/**
	 * This method will reset the south panel.
	 */
	public void reset(){
		speedSlider.setValue(1);
		asteroidAdder.setText("");
		
	}
}
