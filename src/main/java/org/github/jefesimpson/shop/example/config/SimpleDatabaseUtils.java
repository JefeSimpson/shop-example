package org.github.jefesimpson.shop.example.config;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.github.jefesimpson.shop.example.model.Client;
import org.github.jefesimpson.shop.example.model.Employee;
import org.github.jefesimpson.shop.example.model.Order;
import org.github.jefesimpson.shop.example.model.Product;

import java.sql.SQLException;

public class SimpleDatabaseUtils implements DatabaseUtils {
    static final String JDBC_CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\alua-\\OneDrive\\Рабочий стол";
    private static ConnectionSource connectionSource = null;

    static {
        try {
            connectionSource = new JdbcPooledConnectionSource(JDBC_CONNECTION_STRING);

            TableUtils.createTableIfNotExists(connectionSource, Client.class);
            TableUtils.createTableIfNotExists(connectionSource, Employee.class);
            TableUtils.createTableIfNotExists(connectionSource, Order.class);
            TableUtils.createTableIfNotExists(connectionSource, Product.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Unable to initialize database configuration");
        }
    }

    @Override
    public String jdbcConnectionString() {
        return JDBC_CONNECTION_STRING;
    }

    @Override
    public ConnectionSource connectionSource() {
        if (connectionSource == null)
            throw new RuntimeException("Have not initialized ConnectionSource properly");
        return connectionSource;
    }
}
