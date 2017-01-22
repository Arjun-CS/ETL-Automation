package handlingHomePageNavigation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import basePage.HomePage;
import compareTables.TopWindow;

public class OpenQueryBuilder implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(TopWindow.topWindowPointer==null)
		{
			TopWindow.topWindowPointer=new TopWindow();
			//TopWindow.topWindowPointer.compareFrame.toFront();
			//HomePage.homePagePointer.frame.toBack();
		}
		else
		{
			TopWindow.topWindowPointer.compareFrame.setVisible(false);
			TopWindow.topWindowPointer=new TopWindow();
			//TopWindow.topWindowPointer.compareFrame.toFront();
			//HomePage.homePagePointer.frame.toBack();
		}
	}

}
