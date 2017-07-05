package framework.callback;

public abstract class ObjectReturn <T> {
	
	public void Callback(final T Object){
	}
	
	public void CallbackException(final BusinessRuleException e) {
	}	
}
