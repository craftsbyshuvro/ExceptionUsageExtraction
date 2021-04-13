import java.awt.Button;
import java.util.ArrayList;
import java.util.List;

public class ExceptionDetails {
	public String ProjectName;
	public String ExceptionType;
	public String FilePath;
	public List<String> InvokedMethods = new ArrayList<String>();
	public String InstanceCreationInThrowStatement;
	public boolean IsCustomExceptionThrown;
	}

