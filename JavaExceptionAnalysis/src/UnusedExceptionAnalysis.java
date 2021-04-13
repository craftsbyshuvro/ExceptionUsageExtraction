import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

public class UnusedExceptionAnalysis {
	public void AnalyzeUnusedException(String pFilePath) throws Exception {
		String content = FileUtils.readFileToString( new File(pFilePath));
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(content.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		List<ExceptionDetails> exDetailsList = new ArrayList<ExceptionDetails>();

		cu.accept(new ASTVisitor() {
			SimpleName variableName;
			String ExceptionType;
			List<SimpleName> ListvariableName;
			List<String> invokedMethods;

			@Override
			public boolean visit(CatchClause node) {
				ListvariableName = new ArrayList<SimpleName>();
				variableName = (SimpleName) null;
				invokedMethods = new ArrayList<String>();
				
				ExceptionType = node.getException().getType().toString();
				node.accept(new ASTVisitor() {
					@Override
					public boolean visit(SingleVariableDeclaration node) {
						variableName = node.getName();
						return true;
					}
					
					@Override
					public boolean visit(MethodInvocation node) {
						invokedMethods.add(node.getName().toString());
						return true;
					}
					
					@Override
					public boolean visit(SimpleName node) {
							ListvariableName.add(node);
						return true;
					}
				});

				
				int numCount = 0;
				for (SimpleName thisNum : ListvariableName) {
			        if (thisNum.toString().equals(variableName.toString())) {
			        	numCount++;
			        }
			    }
								
			    if(numCount < 2 && invokedMethods.size()>0) {
					System.out.println(ExceptionType+" "+variableName+" is never used in catch block");
					System.out.print(pFilePath);
			    }
			    ListvariableName  = new ArrayList<SimpleName>();
			    
			    
			    
			    
				return true;
			}

		});
	}
}
