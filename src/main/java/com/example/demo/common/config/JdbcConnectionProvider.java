package com.example.demo.common.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class JdbcConnectionProvider {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    }
}
