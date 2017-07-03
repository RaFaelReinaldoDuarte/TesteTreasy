package FRAMEWORK.CALLBACK;

import java.util.List;

public abstract class ListReturn<T> {

    public void Callback(final List<T> list) {
    }

    public void CallbackException(final BusinessRuleException e) {
    }

}
