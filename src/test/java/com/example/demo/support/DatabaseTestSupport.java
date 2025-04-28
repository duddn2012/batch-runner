package com.example.demo.support;

import java.sql.Connection;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseTestSupport {

    private final DataSource dataSource;

    public void executeSql(String path) throws Exception {
        try(Connection connection = dataSource.getConnection()) {
            Resource resource = new ClassPathResource(path);
            ScriptUtils.executeSqlScript(connection, resource);
        }
    }
}
