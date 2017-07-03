package FRAMEWORK.CALLBACK;

public class BusinessRuleException extends Error {
	private static final long serialVersionUID = -5755368701266603509L;
	
	public BusinessRuleException(String message) {
		super(message, null);
	}
	
	public BusinessRuleException(Throwable e) {
		super(e);
	}
	
	public BusinessRuleException(String message, Throwable e) {
		super(message, e);
	}
}
