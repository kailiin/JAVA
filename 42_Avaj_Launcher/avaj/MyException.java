package avaj;

public class MyException extends Exception {
	
	private static final long serialVersionUID = 4239706358105725327L;

	public MyException() {}
	
	public MyException(String msg) {
		super(msg);
	}
	
    public MyException(Throwable cause) {
        super (cause);
    }

    public MyException(String msg, Throwable cause) {
        super (msg, cause);
    }

}
