package com.clarusfinance.infrastructure.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    Connection open() throws SQLException;
}
