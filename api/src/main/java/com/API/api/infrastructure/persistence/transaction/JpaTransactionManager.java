package com.API.api.infrastructure.persistence.transaction;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.API.api.application.port.TransactionManager;

import jakarta.transaction.Transactional;

@Component
public class JpaTransactionManager implements TransactionManager {

    @Override
    @Transactional
    public <T> T execute(Supplier<T> action) {
        return action.get();
    }
}
