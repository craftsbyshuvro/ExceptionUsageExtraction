import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Program {
	public static void main(String []args) throws Exception {
	
		
//		This is to extract exception Details
		extractExceptionDetails();
		
//		This is to analyze UnusedExceeption Object
//		analyzeUnusedExceptionObject();


		//This is to analyze finally block
//		analyzeFinallyBlock();
	}
	
	public static void analyzeFinallyBlock() throws Exception {
		
		FinallyAnalysis anfb = new FinallyAnalysis();

		Parser parser = new Parser();
		File f = new File(Parser.CSVFileLocation);
		parser.CreateCSVConditionally(f);
		
		String ProjectsLocationPath = "D:\\Graduate Seminar\\Projects";
		List<ProjectDetails> allprojectsDetails = new ArrayList<ProjectDetails>();
		File file = new File(ProjectsLocationPath);
		String[] names = file.list();

		for(String name : names)
		{
		    if (new File(ProjectsLocationPath+"\\" + name).isDirectory())
		    {
		    	ProjectDetails pd = new ProjectDetails();
		    	pd.ProjectPath = ProjectsLocationPath+"\\" + name;
		    	pd.ProjectName = name;
		    	allprojectsDetails.add(pd);
		    }
		}
		
		DirectoryExplorer de = new DirectoryExplorer();
		for(ProjectDetails projectDtls : allprojectsDetails) {
			List<String> javaFiles = de.GetAllJavaFiles(projectDtls.ProjectPath);
			for(String filePath : javaFiles) {
				anfb.AnalyzeFinallyBlock(filePath);
			}
		}
	}
	
	public static void analyzeUnusedExceptionObject() throws Exception {

		UnusedExceptionAnalysis unusedExceptionanalysis = new UnusedExceptionAnalysis();
		
		Parser parser = new Parser();
		File f = new File(Parser.CSVFileLocation);
		parser.CreateCSVConditionally(f);
		
		String ProjectsLocationPath = "D:\\Graduate Seminar\\Projects";
		List<ProjectDetails> allprojectsDetails = new ArrayList<ProjectDetails>();
		File file = new File(ProjectsLocationPath);
		String[] names = file.list();

		for(String name : names)
		{
		    if (new File(ProjectsLocationPath+"\\" + name).isDirectory())
		    {
		    	ProjectDetails pd = new ProjectDetails();
		    	pd.ProjectPath = ProjectsLocationPath+"\\" + name;
		    	pd.ProjectName = name;
		    	allprojectsDetails.add(pd);
		    }
		}
		
		DirectoryExplorer de = new DirectoryExplorer();
		for(ProjectDetails projectDtls : allprojectsDetails) {
			List<String> javaFiles = de.GetAllJavaFiles(projectDtls.ProjectPath);
			for(String filePath : javaFiles) {
				unusedExceptionanalysis.AnalyzeUnusedException(filePath);
				parser.AnalyzeJavaCode(filePath,projectDtls.ProjectName);
			}
		}
	}
	
	public static void  extractExceptionDetails() throws Exception {
		Parser parser = new Parser();
		File f = new File(Parser.CSVFileLocation);
		parser.CreateCSVConditionally(f);
		
		String ProjectsLocationPath = "D:\\Study\\Graduate Seminar\\Projects";
		List<ProjectDetails> allprojectsDetails = new ArrayList<ProjectDetails>();
		File file = new File(ProjectsLocationPath);
		String[] names = file.list();

		for(String name : names)
		{
		    if (new File(ProjectsLocationPath+"\\" + name).isDirectory())
		    {
		    	ProjectDetails pd = new ProjectDetails();
		    	pd.ProjectPath = ProjectsLocationPath+"\\" + name;
		    	pd.ProjectName = name;
		    	allprojectsDetails.add(pd);
		    }
		}
		
		DirectoryExplorer de = new DirectoryExplorer();
		for(ProjectDetails projectDtls : allprojectsDetails) {
			List<String> javaFiles = de.GetAllJavaFiles(projectDtls.ProjectPath);
			for(String filePath : javaFiles) {
				parser.AnalyzeJavaCode(filePath,projectDtls.ProjectName);
			}
		}
	}
	
}
