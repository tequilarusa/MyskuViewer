package com.iamakulov.myskusdk;

public interface MyskuCallback<T> {
    void onSuccess(T result);

    void onError(MyskuError error);
}
