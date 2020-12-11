package org.github.jefesimpson.shop.example.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import org.github.jefesimpson.shop.example.config.SimpleDatabaseUtils;
import org.github.jefesimpson.shop.example.model.Order;

import java.sql.SQLException;

public class OrderService implements Service<Order>{
    @Override
    public Dao<Order, Integer> dao() throws SQLException {
        return DaoManager.createDao(SimpleDatabaseUtils.connectionSource(), Order.class);
    }
}
