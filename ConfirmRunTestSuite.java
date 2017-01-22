package basePage;

import handlingHomePageNavigation.ConfirmRunTestSuiteSelected;
import handlingHomePageNavigation.RejectRunTestSuiteSelected;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConfirmRunTestSuite {

	public static ConfirmRunTestSuite ConfirmRunTestSuitePointer=null;
	public JFrame confirmRunTestSuiteFrame=null;
	public JLabel confirmTestSuiteLabel=new JLabel("do you want to run entire test suite now:");
	public JButton yesButton=null;
	public JButton noButton=null;
	public ConfirmRunTestSuite()
	{
		confirmRunTestSuiteFrame=new JFrame("Confirmation Screen");
		confirmRunTestSuiteFrame.setLayout(new FlowLayout());
		confirmRunTestSuiteFrame.setSize(300,120);
		yesButton=new JButton("YES");
		noButton=new JButton("NO");
		confirmRunTestSuiteFrame.add(confirmTestSuiteLabel);
		confirmRunTestSuiteFrame.add(yesButton);
		confirmRunTestSuiteFrame.add(noButton);
		confirmRunTestSuiteFrame.setVisible(true);
		yesButton.addActionListener(new ConfirmRunTestSuiteSelected());
		noButton.addActionListener(new RejectRunTestSuiteSelected());
	}
}
