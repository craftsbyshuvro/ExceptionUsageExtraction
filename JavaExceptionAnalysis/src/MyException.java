
public class MyException extends Exception {
	String name = "";
	Parser h = new Parser();
    public MyException(String errorMessage) {
        super(errorMessage);
    }
}