import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TextBlock;
import org.eclipse.jdt.core.dom.TryStatement;

public class FinallyAnalysis {
	int TryBlockCount = 0;
	int FinallyBlockCount = 0;

	
	
	public void AnalyzeFinallyBlock(String pFilePath) throws Exception {
//		pFilePath = "D:\\Graduate Seminar\\JavaExceptionAnalysis\\src\\SampleClass.java";

		String content = FileUtils.readFileToString( new File(pFilePath));
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(content.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			@Override
			public boolean visit(TryStatement node) {
				++TryBlockCount;
				
				Block FinallyBlock = node.getFinally();
				
				if(FinallyBlock!=null) {
					++FinallyBlockCount;
				}else {
					return true;
				}
				
				
				node.getFinally().accept(new ASTVisitor() {
					@Override
					public boolean visit(Block node) {
//						System.out.println("YES");
						return true;
					}
					@Override
					public boolean visit(MethodInvocation node) {
						System.out.println(node.getName().toString()+",");
						return true;
					}
				});
				return true;
			}
			
		});
		
//		System.out.println("Total Try Block: "+TryBlockCount);
//		System.out.println("Total Finally Block: "+FinallyBlockCount);
	}
}
