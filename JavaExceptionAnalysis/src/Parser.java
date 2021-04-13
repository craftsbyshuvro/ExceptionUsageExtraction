import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;


public class Parser {
	
	static String CSVFileLocation = "D:\\Study\\Graduate Seminar\\Data\\ExceptionsDetails.csv";
	String ProName;
	int ProcessCount = 0;
	public void AnalyzeJavaCode(String pFilePath, String ProjectName) throws Exception {
		ProName = ProjectName;
		String content = FileUtils.readFileToString( new File(pFilePath));
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(content.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		List<ExceptionDetails> exDetailsList = new ArrayList<ExceptionDetails>();

		cu.accept(new ASTVisitor() {
			@Override
			public boolean visit(CatchClause node) {
				ExceptionDetails exDetails = new ExceptionDetails();		
				exDetails.ExceptionType = node.getException().getType().toString();
				exDetails.FilePath = pFilePath;
				node.accept(new ASTVisitor() {
					@Override
					public boolean visit(MethodInvocation node) {
						exDetails.InvokedMethods.add(node.getName().toString());
						return true;
					}

					@Override
					public boolean visit(ThrowStatement node) {
						node.accept(new ASTVisitor() {
							@Override
							public boolean visit(ClassInstanceCreation node) {
								exDetails.InstanceCreationInThrowStatement = node.getType().toString();
								exDetails.IsCustomExceptionThrown = isCustomException(exDetails.InstanceCreationInThrowStatement);
								return true;
							}

						});
						return true;
					}
					
				});
				
				exDetailsList.add(exDetails);
				return true;
			}
			
		});
		
		WriteContentToCSV(exDetailsList);

	}
	
	public boolean isCustomException(String pClassName) {		
		List<String> vSystemExceptions = Arrays.asList("Error","AssertionError","LinkageError","BootstrapMethodError","ClassCircularityError","ClassFormatError","UnsupportedClassVersionError","ExceptionInInitializerError","IncompatibleClassChangeError","AbstractMethodError","IllegalAccessError","InstantiationError","NoSuchFieldError","NoSuchMethodError","NoClassDefFoundError","UnsatisfiedLinkError","VerifyError","ThreadDeath","VirtualMachineError","InternalError","OutOfMemoryError","StackOverflowError","UnknownError","Exception","CloneNotSupportedException","InterruptedException","IOException","FileNotFoundException","SocketException","ConnectException","UnknownHostException","ReflectiveOperationException","ClassNotFoundException","IllegalAccessException","InstantiationException","InvocationTargetException","NoSuchFieldException","NoSuchMethodException","RuntimeException","ArithmeticException","ArrayStoreException","ClassCastException","ConcurrentModificationException","EnumConstantNotPresentException","IllegalArgumentException","IllegalThreadStateException","NumberFormatException","IllegalMonitorStateException","IllegalStateException","IndexOutOfBoundsException","ArrayIndexOutOfBoundsException","StringIndexOutOfBoundsException","NegativeArraySizeException","NullPointerException","SecurityException","TypeNotPresentException","UnsupportedOperationException");
		
		if(vSystemExceptions.contains(pClassName)) {
			return false;
		}

		return true;
	}
	
	
	public void WriteContentToCSV(List<ExceptionDetails> excepDetails) throws Exception {	
		FileWriter csvwriter = new FileWriter(CSVFileLocation, true);
		
		for(ExceptionDetails exDetails : excepDetails) {
			csvwriter.append(ProName);
	        csvwriter.append(",");
	        csvwriter.append(exDetails.FilePath);
	        csvwriter.append(",");
	        csvwriter.append(exDetails.ExceptionType);
	        csvwriter.append(",");
	        csvwriter.append(String.join("|", exDetails.InvokedMethods));
	        csvwriter.append(",");
	        
			if(exDetails.InstanceCreationInThrowStatement == null) {
				csvwriter.append("");
			}else {
		        csvwriter.append(exDetails.InstanceCreationInThrowStatement);
			}
	        csvwriter.append(",");
	        
	    	if(exDetails.InstanceCreationInThrowStatement == null) {
		        csvwriter.append("");
	    	}else {
		        csvwriter.append(String.valueOf(exDetails.IsCustomExceptionThrown));
	    	}
	        csvwriter.append("\n");
		}
        csvwriter.close();
	}
	
	
	public void CreateCSVConditionally(File f) throws IOException {	
		
		if(f.exists()){
    	   File csvFile = new File(CSVFileLocation); 
    	   csvFile.delete();
		}
		
		   f.createNewFile();
			FileWriter csvwriter = new FileWriter(CSVFileLocation, true);
			csvwriter.append("ProjectName");
	        csvwriter.append(",");
			csvwriter.append("FilePath");
	        csvwriter.append(",");
			csvwriter.append("ExceptionType");
	        csvwriter.append(",");
	        csvwriter.append("InvokedMethods");
	        csvwriter.append(",");
	        csvwriter.append("ThrowException");
	        csvwriter.append(",");
	        csvwriter.append("IsCustomerExceptionThrown");
	        csvwriter.append("\n");
            csvwriter.close();
	}
	
}
