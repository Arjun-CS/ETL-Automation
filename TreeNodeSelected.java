package handlingHomePageNavigation;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import basePage.ConfirmRunTestSuite;

public class TreeNodeSelected implements TreeSelectionListener{

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("selected");
		ConfirmRunTestSuite.ConfirmRunTestSuitePointer=new ConfirmRunTestSuite();
	}

}
