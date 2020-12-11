package org.github.jefesimpson.shop.example.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import org.github.jefesimpson.shop.example.config.SimpleDatabaseUtils;
import org.github.jefesimpson.shop.example.model.Product;

import java.sql.SQLException;

public class ProductService implements Service<Product>{
    @Override
    public Dao<Product, Integer> dao() throws SQLException {
        return DaoManager.createDao(SimpleDatabaseUtils.connectionSource(), Product.class);
    }
}
