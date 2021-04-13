import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CustomExceptionAnalyzer {
	public void ExceptionInheritenceAnalyse() throws IOException {
		 String pFilePath = "D:\\Graduate Seminar\\JavaExceptionAnalysis\\src\\MyException.java";

		String content = FileUtils.readFileToString( new File(pFilePath));
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(content.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		List<ExceptionDetails> exDetailsList = new ArrayList<ExceptionDetails>();

		cu.accept(new ASTVisitor() {
			@Override
			public boolean visit(TypeDeclaration node) {
				System.out.println(node.getName());
				System.out.println(node.getSuperclassType());
				return true;
			}

		});
	}
}
