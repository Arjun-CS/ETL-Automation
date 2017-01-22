package handlingHomePageNavigation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import basePage.ConfirmRunTestSuite;

public class RejectRunTestSuiteSelected implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ConfirmRunTestSuite.ConfirmRunTestSuitePointer.confirmRunTestSuiteFrame.setVisible(false);
	}

}
