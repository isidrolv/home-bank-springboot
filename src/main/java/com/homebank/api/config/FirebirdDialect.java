package com.homebank.api.config;

import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.AbstractDialect;
import org.springframework.data.relational.core.dialect.LimitClause;
import org.springframework.data.relational.core.dialect.LockClause;
import org.springframework.data.relational.core.sql.LockOptions;

public class FirebirdDialect extends AbstractDialect implements Dialect {
    @Override
    public LimitClause limit() {
        return new EmptyLimitClause();
    }

    @Override
    public LockClause lock() {
        return new EmptyLockClause();
    }

    // Extracted class for Empty LimitClause
    private static class EmptyLimitClause implements LimitClause {
        @Override
        public String getLimit(long limit) {
            return "";
        }

        @Override
        public String getOffset(long offset) {
            return "";
        }

        @Override
        public String getLimitOffset(long limit, long offset) {
            return "";
        }

        @Override
        public Position getClausePosition() {
            return null;
        }

    }

    // Extracted class for Empty LockClause
    private static class EmptyLockClause implements LockClause {
        @Override
        public String getLock(LockOptions lockOptions) {
            return "";
        }

        @Override
        public Position getClausePosition() {
            return null;
        }

    }

}