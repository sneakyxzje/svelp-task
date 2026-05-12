package com.API.api.application.port;

import java.util.function.Supplier;

public interface TransactionManager {
    <T> T execute(Supplier<T> action);
}
