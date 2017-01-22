package handlingHomePageNavigation;

import java.io.IOException;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import basePage.HomePage;

public class TreeNodeSelectedToDisplayResults implements TreeSelectionListener{

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println(HomePage.homePagePointer.testResultsTree.getSelectionPath().getPath().toString());
		//System.out.println(arg0.getPath());
		String path=arg0.getPath().toString().replaceAll(", ","\\\\");
		//System.out.println(path);
		path=".\\"+path.substring(1, path.length()-1);
		System.out.println(path);
		
		//to open the notepad file in text editor mode from java(ideally it opens a notepad file  in windows)
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("notepad.exe "+path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
