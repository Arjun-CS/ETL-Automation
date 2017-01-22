package basePage;

import handlingHomePageNavigation.OpenQueryBuilder;
import handlingHomePageNavigation.TreeNodeSelected;
import handlingHomePageNavigation.TreeNodeSelectedToDisplayResults;
import helper.Database;
import helper.GetData;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class HomePage {
	public static HomePage homePagePointer=null;
	
	
	
	/*public JFrame testSuiteFrame=null;
	public JTree testSuiteTree=null;
	public HomePage()
	{
		testSuiteFrame=new JFrame("TAD");
		
		//testSuiteTree.
		
		File testSuiteDirectory=new File(".\\TestSuite");
		String[] listOfTestSuites=testSuiteDirectory.list();
		
		for(int i=0;i<listOfTestSuites.length;i++)
		{
			System.out.println(listOfTestSuites[i]);
			DefaultMutableTreeNode testSuites=new DefaultMutableTreeNode(listOfTestSuites[i]);
			//testSuiteTree.ad
		}
		testSuiteTree=new JTree();
		testSuiteFrame.add(testSuiteTree);
		testSuiteFrame.setVisible(true);
	}
	public static void main(String[] args)
	{
		HomePage hp=new HomePage();
	}
*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public JFrame frame=null;
	public JPanel topLeftPanel=null;
	public JPanel topRightPanel=null;
	public JPanel bottomLeftPanel=null;
	public JPanel bottomRightPanel=null;
	
	private DefaultMutableTreeNode testSuiteRoot;
	
	//
	private DefaultMutableTreeNode testResultsRoot;

    private DefaultTreeModel testSuiteTreeModel;
    
    //
    private DefaultTreeModel testResultsTreeModel;

    public JTree testSuiteTree;
    
    //
    public JTree testResultsTree;

    public Object path[]=null;
    public String testSuitePathToRun=new String("");
    public StringBuffer folderStructureBuffer=new StringBuffer("");
    public String currentFolderStructureBuffer=new String("");
    public String testResultFolderStructurePath=new String("");
    
    
    
    public String DBHost = null;
    public String DBPort;
    public String DBSid = null;
    public String DBUserName = null;
    public String DBPassword = null;
    public File[] TestSuiteFiles=null;
    
    public Connection dbConnection=null;
    
    public List<String> sqlCommands=new ArrayList<String>();
    public int queryPosition=0;
    String sqlQuery=null;
    String countResult=null;
    
    public JButton queryBuilderButton=null;
    
    
    public HomePage() {
        frame = new JFrame("QC and Automation Combined");
	    /*    
        topLeftPanel=new JPanel();
	        topRightPanel=new JPanel();
	        bottomLeftPanel=new JPanel();
	        bottomRightPanel=new JPanel();
	        frame.setLayout(new FlowLayout());
	        */
        frame.setLayout(new GridLayout());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        File testSuiteFileRoot = new File(".\\TestSuite");
        //
        File testResultsFileRoot=new File(".\\TestResults")   ;
        
        testSuiteRoot = new DefaultMutableTreeNode(new FileNode(testSuiteFileRoot));
        
        //
        testResultsRoot=new DefaultMutableTreeNode(new FileNode(testResultsFileRoot));
        
        testSuiteTreeModel = new DefaultTreeModel(testSuiteRoot);
        
        //
        testResultsTreeModel = new DefaultTreeModel(testResultsRoot);

        testSuiteTree = new JTree(testSuiteTreeModel);
        
        //
        testResultsTree = new JTree(testResultsTreeModel);
        
        testSuiteTree.setShowsRootHandles(true);
        
        //
        testResultsTree.setShowsRootHandles(true);
        
        
        JScrollPane scrollPane = new JScrollPane(testSuiteTree);
        
        //
        JScrollPane testResultsScrollPane = new JScrollPane(testResultsTree);
        
        testSuiteTree.addTreeSelectionListener(new TreeNodeSelected());
        
        
        testResultsTree.addTreeSelectionListener(new TreeNodeSelectedToDisplayResults());
        //topLeftPanel.add(scrollPane);
        frame.add(scrollPane);
        
        
        //
        frame.add(testResultsScrollPane);
        
        /*
        frame.add(topLeftPanel);
        frame.add(topRightPanel);
        frame.add(bottomLeftPanel);
        frame.add(bottomRightPanel);
        */
        frame.setLocationByPlatform(true);
        frame.setSize(640, 480);
        frame.setVisible(true);

        CreateChildNodes ccn = 
                new CreateChildNodes(testSuiteFileRoot, testSuiteRoot);
        new Thread(ccn).start();
        
        CreateChildNodes ccn1 = 
                new CreateChildNodes(testResultsFileRoot, testResultsRoot);
        new Thread(ccn1).start();
        if(queryBuilderButton==null)
        	queryBuilderButton=new JButton("Query Builder");
        else
        {
        	queryBuilderButton.setVisible(false);
        	queryBuilderButton=new JButton("Query Builder");
        }
        frame.add(queryBuilderButton);
        queryBuilderButton.addActionListener(new OpenQueryBuilder());
    }

    public static void main(String[] args) {
    	HomePage.homePagePointer=new HomePage();
    }

    public class CreateChildNodes implements Runnable {

        private DefaultMutableTreeNode root;

        private File fileRoot;

        public CreateChildNodes(File fileRoot, 
                DefaultMutableTreeNode root) {
            this.fileRoot = fileRoot;
            this.root = root;
        }

        @Override
        public void run() {
            createChildren(fileRoot, root);
        }

        private void createChildren(File fileRoot, 
                DefaultMutableTreeNode node) {
            File[] files = fileRoot.listFiles();
            if (files == null) return;

            for (File file : files) {
                DefaultMutableTreeNode childNode = 
                        new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
                if (file.isDirectory()) {
                    createChildren(file, childNode);
                }
            }
        }

    }

    public class FileNode {

        private File file;

        public FileNode(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            String name = file.getName();
            if (name.equals("")) {
                return file.getAbsolutePath();
            } else {
                return name;
            }
        }
    }

    
    @SuppressWarnings("deprecation")
	public void runTestSuite() throws ClassNotFoundException, IOException
    {
    	testSuitePathToRun=new String("");
    	folderStructureBuffer=new StringBuffer("");
    	//HomePage.homePagePointer.;
    	path=HomePage.homePagePointer.testSuiteTree.getSelectionPath().getPath();
    	for(int i=0;i<path.length;i++)
    	{
    		testSuitePathToRun=testSuitePathToRun+path[i].toString()+"\\\\";
    	}
    	testSuitePathToRun=".\\\\"+testSuitePathToRun.substring(0,testSuitePathToRun.length()-2);
    	//System.out.println(testSuitePathToRun);
    	//System.out.println(HomePage.homePagePointer.testSuiteTree.getLastSelectedPathComponent());; 
    	//File f=new File(".\\"+testSuitePathToRun);
    	boolean first=true;
    	for(int i=0;i<path.length;i++)
    	{
    		if(first!=true)
    		{
    			folderStructureBuffer.append("\\");
    			folderStructureBuffer.append(path[i].toString());
    		}
    		else
    		{
    			folderStructureBuffer.append(".\\TestResults\\");
    			folderStructureBuffer.append(path[i].toString());
    			first=false;
    		}
    		//System.out.println("*"+folderStructureBuffer+"*");
    		testResultFolderStructurePath=folderStructureBuffer.toString();
    		File resultPath=new File(testResultFolderStructurePath);
    		if((!resultPath.exists()) && (!resultPath.toString().contains(".txt")) && (!resultPath.toString().contains(".properties")))
    		{
    			resultPath.mkdir();
    		}
    	}
    	
    	
    	
    	
    	//
    	System.out.println("*"+testSuitePathToRun+"*");
    	if(testSuitePathToRun.contains(".txt"))
    	{
    		queryPosition=0;
    		int folder=testSuitePathToRun.lastIndexOf("\\\\");
    		System.out.println("*"+testSuitePathToRun.substring(0,folder)+"*");
    		File f=new File(testSuitePathToRun.substring(0,folder));
    		TestSuiteFiles=f.listFiles();
    		boolean firstTimeConnection=true;
    		for(int i=0;i<TestSuiteFiles.length;i++)
    		{
    			if((TestSuiteFiles[i].getName().contains(".properties")) && (firstTimeConnection==true))
    			{
    				firstTimeConnection=false;
    				//System.out.println(TestSuiteFiles[i].getName());
    				//System.out.println(testSuitePathToRun.substring(0,folder)+"\\"+TestSuiteFiles[i].getName());
    				readPropertiesFile(testSuitePathToRun.substring(0,folder)+"\\\\"+TestSuiteFiles[i].getName());
    				dbConnection=Database.connect(DBHost, DBPort, DBSid, DBUserName, DBPassword);
    				//System.out.println(DBHost+DBPort+DBSid+DBUserName+DBPassword);
    			}
    		}
    		sqlCommands=GetData.fromFile(testSuitePathToRun);
    		
    		/*Iterator<String> it=sqlCommands.iterator();
    		while(it.hasNext())
    		{
    			System.out.println(it.next());
    		}*/
    		
    		currentFolderStructureBuffer=folderStructureBuffer.toString().substring(0,folderStructureBuffer.length()-4);
    		System.out.println("*currentFolderStructureBuffer:"+currentFolderStructureBuffer);
    		Date d=new Date();
			String date=d.getDate()+"_"+(d.getMonth()+1)+"_"+(d.getYear()+1900)+"_"+d.getHours()+"_"+d.getMinutes()+"_"+d.getSeconds();
			File of=new File(currentFolderStructureBuffer+date+".txt");
			System.out.println("*"+currentFolderStructureBuffer+date+".txt");
			
			of.createNewFile();
			FileWriter fw=new FileWriter(of);
			BufferedWriter bw=new BufferedWriter(fw);
			sqlQuery=sqlCommands.get(queryPosition);
			System.out.println(sqlQuery);
			try {
				countResult=Database.executeQueryGetCountofTable(dbConnection,sqlQuery);
				System.out.println("stage Count:"+sqlQuery+" :: "+countResult);
				bw.write("stage Count:"+sqlQuery+" :: "+countResult);
				bw.newLine();
			} catch (SQLException|NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				bw.write("source query is not correct please check it");
				bw.newLine();
			}
			//System.out.println("stage Count:"+sqlQuery+" :: "+countResult);
			//bw.write("stage Count:"+sqlQuery+" :: "+countResult);
			//bw.newLine();
			
			
			queryPosition++;
			sqlQuery=sqlCommands.get(queryPosition);
			try {
				countResult=Database.executeQueryGetCountofTable(dbConnection,sqlQuery);
				bw.newLine();
				bw.write("target Count:"+sqlQuery+" :: "+countResult);
				bw.newLine();
				bw.newLine();
				bw.newLine();
			} catch (SQLException|NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				bw.write("target query is not correct please check it");
				bw.newLine();
			}
			//System.out.println("target Count:"+sqlQuery+" :: "+countResult);
			
			//bw.newLine();
			//bw.write("target Count:"+sqlQuery+" :: "+countResult);
			//bw.newLine();
			//bw.newLine();
			//bw.newLine();
			
			queryPosition++;
			sqlQuery=new String("");
			sqlQuery=sqlQuery+sqlCommands.get(queryPosition);
			queryPosition++;
			sqlQuery=sqlQuery+" "+sqlCommands.get(queryPosition);
			queryPosition++;
			sqlQuery=sqlQuery+" "+sqlCommands.get(queryPosition);
			String[][] minusResult;
			try {
				minusResult = Database.executeGivenQuery(dbConnection,sqlQuery);
				bw.write(sqlQuery);
				bw.newLine();
				bw.newLine();
				bw.write("mismatch Records are as below:");
				bw.newLine();
				bw.newLine();
				boolean recordExists=true;
				for(int l=0;l<minusResult.length;l++)
				{
					String record=new String("");
					for(int m=0;m<minusResult[l].length;m++)
					{
						record=record+minusResult[l][m]+"|";
					}
					if(minusResult[l][0]!=null)
						bw.write(record);
					bw.newLine();
				}
			} catch (SQLException|NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				bw.write(sqlQuery);
				bw.newLine();
				bw.newLine();
				bw.write("minus query is not correct please check it");
				bw.newLine();
			}
			//System.out.println("minus query: "+sqlQuery);
			//bw.write("mismatch Records are as below:");
			//bw.newLine();
			//bw.newLine();
			//boolean recordExists=true;
			//for(int l=0;l<minusResult.length;l++)
			//{
				//String record=new String("");
				//for(int m=0;m<minusResult[l].length;m++)
				//{
				//	record=record+minusResult[l][m]+"|";
				//}
				//if(minusResult[l][0]!=null)
				//	bw.write(record);
				//bw.newLine();
			//}
			bw.flush();
			fw.flush();
			testResultsDisplay();
			
			
			
    	}
    	else if(testSuitePathToRun.contains(".properties"))
    	{
    		
    	}
    	else
    	{
    	
    	
    	
    	//
    	
    	
    	
    	File f=new File(testSuitePathToRun);
		TestSuiteFiles=f.listFiles();
		boolean firstTimeConnection=true;
		for(int i=0;i<TestSuiteFiles.length;i++)
		{
			if((TestSuiteFiles[i].getName().contains(".properties")) && (firstTimeConnection==true))
			{
				firstTimeConnection=false;
				//System.out.println(TestSuiteFiles[i].getName());
				readPropertiesFile(testSuitePathToRun+"\\"+TestSuiteFiles[i].getName());
				dbConnection=Database.connect(DBHost, DBPort, DBSid, DBUserName, DBPassword);
				//System.out.println(DBHost+DBPort+DBSid+DBUserName+DBPassword);
			}
			else if(!(TestSuiteFiles[i].getName().contains(".properties")))
			{
				//SetData.createWorkbook(new File(testSuitePathToRun+"\\"+TestSuiteFiles[i].getName()));
				sqlCommands=GetData.fromFile(testSuitePathToRun+"\\"+TestSuiteFiles[i].getName());
				for(int j=0;j<sqlCommands.size();j++)
				{
					//System.out.println(sqlCommands.get(j));
				}
				
				currentFolderStructureBuffer=folderStructureBuffer.toString();
				//System.out.println(TestSuiteFiles[i].getName());
				Date d=new Date();
				String date=d.getDate()+"_"+(d.getMonth()+1)+"_"+(d.getYear()+1900)+"_"+d.getHours()+"_"+d.getMinutes()+"_"+d.getSeconds();
				//File of=new File(currentFolderStructureBuffer.append("\\").append(TestSuiteFiles[i].getName().substring(0,TestSuiteFiles[i].getName().length()-4)).append(date).append(".txt").toString());
				File of=new File(currentFolderStructureBuffer+"\\"+TestSuiteFiles[i].getName().substring(0,TestSuiteFiles[i].getName().length()-4)+date+".txt");
				of.createNewFile();
				FileWriter fw=new FileWriter(of);
				BufferedWriter bw=new BufferedWriter(fw);
				
				sqlQuery=sqlCommands.get(queryPosition);
				try {
					countResult=Database.executeQueryGetCountofTable(dbConnection,sqlQuery);
					bw.write("stage Count:"+sqlQuery+" :: "+countResult);
					bw.newLine();
				} catch (SQLException|NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					bw.write("source query is not correct please check it");
					bw.newLine();
				}
				//System.out.println("stage Count:"+sqlQuery+" :: "+countResult);
				//bw.write("stage Count:"+sqlQuery+" :: "+countResult);
				//bw.newLine();
				
				
				queryPosition++;
				sqlQuery=sqlCommands.get(queryPosition);
				try {
					countResult=Database.executeQueryGetCountofTable(dbConnection,sqlQuery);
					bw.newLine();
					bw.write("target Count:"+sqlQuery+" :: "+countResult);
					bw.newLine();
					bw.newLine();
					bw.newLine();
				} catch (SQLException|NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					bw.write("target query is not correct please check it");
					bw.newLine();
				}
				//System.out.println("target Count:"+sqlQuery+" :: "+countResult);
				
				//bw.newLine();
				//bw.write("target Count:"+sqlQuery+" :: "+countResult);
				//bw.newLine();
				//bw.newLine();
				//bw.newLine();
				
				queryPosition++;
				sqlQuery=new String("");
				sqlQuery=sqlQuery+sqlCommands.get(queryPosition);
				queryPosition++;
				sqlQuery=sqlQuery+" "+sqlCommands.get(queryPosition);
				queryPosition++;
				sqlQuery=sqlQuery+" "+sqlCommands.get(queryPosition);
				String[][] minusResult;
				try {
					bw.write(sqlQuery);
					bw.newLine();
					bw.newLine();
					minusResult = Database.executeGivenQuery(dbConnection,sqlQuery);
					bw.write("mismatch Records are as below:");
					bw.newLine();
					bw.newLine();
					boolean recordExists=true;
					for(int l=0;l<minusResult.length;l++)
					{
						String record=new String("");
						for(int m=0;m<minusResult[l].length;m++)
						{
							record=record+minusResult[l][m]+"|";
						}
						if(minusResult[l][0]!=null)
							bw.write(record);
						bw.newLine();
					}
				} catch (SQLException|NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					bw.write(sqlQuery);
					bw.newLine();
					bw.newLine();
					bw.write("minus query is not correct please check it");
					bw.newLine();
				}
				//System.out.println("minus query: "+sqlQuery);
				//bw.write("mismatch Records are as below:");
				//bw.newLine();
				//bw.newLine();
				//boolean recordExists=true;
				//for(int l=0;l<minusResult.length;l++)
				//{
				//	String record=new String("");
				//	for(int m=0;m<minusResult[l].length;m++)
				//	{
				//		record=record+minusResult[l][m]+"|";
				//	}
				//	if(minusResult[l][0]!=null)
				//		bw.write(record);
				//	bw.newLine();
				//}
				
				
				
				
				//System.out.println("*"+folderStructureBuffer+"*");
				/*
				currentFolderStructureBuffer=folderStructureBuffer.toString();
				//System.out.println(TestSuiteFiles[i].getName());
				Date d=new Date();
				String date=d.getDate()+"_"+(d.getMonth()+1)+"_"+(d.getYear()+1900)+"_"+d.getHours()+"_"+d.getMinutes()+"_"+d.getSeconds();
				//File of=new File(currentFolderStructureBuffer.append("\\").append(TestSuiteFiles[i].getName().substring(0,TestSuiteFiles[i].getName().length()-4)).append(date).append(".txt").toString());
				File of=new File(currentFolderStructureBuffer+"\\"+TestSuiteFiles[i].getName().substring(0,TestSuiteFiles[i].getName().length()-4)+date+".txt");
				of.createNewFile();
				FileWriter fw=new FileWriter(of);
				BufferedWriter bw=new BufferedWriter(fw);
				
				bw.write("stage Count:"+sqlQuery+" :: "+countResult);
				bw.newLine();
				bw.newLine();
				bw.write("target Count:"+sqlQuery+" :: "+countResult);
				bw.newLine();
				bw.newLine();
				bw.newLine();
				
				bw.write("mismatch Records are as below:");
				bw.newLine();
				bw.newLine();
				boolean recordExists=true;
				for(int l=0;l<minusResult.length;l++)
				{
					String record=new String("");
					for(int m=0;m<minusResult[l].length;m++)
					{
						record=record+minusResult[l][m]+"|";
					}
					if(minusResult[i][0]!=null)
						bw.write(record);
					bw.newLine();
				}
				*/
				bw.flush();
				fw.flush();
				//FileOutputStream 
				//Calendar date = new GregorianCalendar(2012, 9, 5);
				
				//System.out.println(d.getDate()+"_"+(d.getMonth()+1)+"_"+(d.getYear()+1900)+"_"+d.getHours()+"_"+d.getMinutes()+"_"+d.getSeconds());
			}	
			
			queryPosition=0;
		}
		//folderStructureBuffer=new StringBuffer("");
		testResultsDisplay();
    	}
    }
    
    public void readPropertiesFile(String filePath)
    {
    	Properties p = new Properties();
        try {
			p.load(new FileReader(filePath));
			this.DBHost = p.getProperty("hostname");
			this.DBPort = p.getProperty("port");
	        //this.DBPort = Integer.parseInt(tempport);
	        this.DBSid = p.getProperty("serviceName");
	        this.DBUserName = p.getProperty("userName");
	        this.DBPassword = p.getProperty("password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
    
    public void testResultsDisplay()
    {
    	if(frame!=null)
    		frame.setVisible(false);
    	frame = new JFrame("QC and Automation Combined");
	    /*    
        topLeftPanel=new JPanel();
	        topRightPanel=new JPanel();
	        bottomLeftPanel=new JPanel();
	        bottomRightPanel=new JPanel();
	        frame.setLayout(new FlowLayout());
	        */
        frame.setLayout(new GridLayout());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        File testSuiteFileRoot = new File(".\\TestSuite");
        //
        File testResultsFileRoot=new File(".\\TestResults")   ;
        
        testSuiteRoot = new DefaultMutableTreeNode(new FileNode(testSuiteFileRoot));
        
        //
        testResultsRoot=new DefaultMutableTreeNode(new FileNode(testResultsFileRoot));
        
        testSuiteTreeModel = new DefaultTreeModel(testSuiteRoot);
        
        //
        testResultsTreeModel = new DefaultTreeModel(testResultsRoot);

        testSuiteTree = new JTree(testSuiteTreeModel);
        
        //
        testResultsTree = new JTree(testResultsTreeModel);
        
        testSuiteTree.setShowsRootHandles(true);
        
        //
        testResultsTree.setShowsRootHandles(true);
        
        
        JScrollPane scrollPane = new JScrollPane(testSuiteTree);
        
        //
        JScrollPane testResultsScrollPane = new JScrollPane(testResultsTree);
        
        testSuiteTree.addTreeSelectionListener(new TreeNodeSelected());
        
        
        testResultsTree.addTreeSelectionListener(new TreeNodeSelectedToDisplayResults());
        //topLeftPanel.add(scrollPane);
        frame.add(scrollPane);
        
        
        //
        frame.add(testResultsScrollPane);
        
        /*
        frame.add(topLeftPanel);
        frame.add(topRightPanel);
        frame.add(bottomLeftPanel);
        frame.add(bottomRightPanel);
        */
        frame.setLocationByPlatform(true);
        frame.setSize(640, 480);
        frame.setVisible(true);

        CreateChildNodes ccn = 
                new CreateChildNodes(testSuiteFileRoot, testSuiteRoot);
        new Thread(ccn).start();
        
        CreateChildNodes ccn1 = 
                new CreateChildNodes(testResultsFileRoot, testResultsRoot);
        new Thread(ccn1).start();
        
        frame.add(queryBuilderButton);
        queryBuilderButton.addActionListener(new OpenQueryBuilder());
    }
}
