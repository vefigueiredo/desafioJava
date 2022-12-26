package com.desafioJava.core.service;

import com.desafioJava.core.interfaces.DatabaseService;
import com.day.commons.datasource.poolservice.DataSourcePool;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.sql.DataSource;
import java.sql.Connection;


@Component(immediate = true,service = DatabaseService.class)
public class DatabaseServiceImpl implements DatabaseService {
    @Reference
    private DataSourcePool dataSourcePool;

    @Override
    public Connection getConnections() {
        Connection connection=null;
        try{
            DataSource dataSource = (DataSource) dataSourcePool.getDataSource("cadastro");
            connection=dataSource.getConnection();

        } catch (Exception e) {
            e.getMessage();
        }
        return connection;
    }
}
