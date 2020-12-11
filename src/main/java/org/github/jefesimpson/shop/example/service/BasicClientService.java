package org.github.jefesimpson.shop.example.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import org.github.jefesimpson.shop.example.config.SimpleDatabaseUtils;
import org.github.jefesimpson.shop.example.model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BasicClientService implements ClientService{
    @Override
    public Dao<Client, Integer> dao() throws SQLException {
        return DaoManager.createDao(SimpleDatabaseUtils., Client.class);
    }

    @Override
    public Client authenticate(String login, String password) {

        if(!loginExist(login)) {
            throw new RuntimeException("login not exists");
        }
        Client client = findByLogin(login);
        if(client.getToken() != null) {
            throw new RuntimeException("token exists");
        }
        if(BCrypt.checkpw(password, client.getPassword())) {
            SecretGenerator secretGenerator = new DefaultSecretGenerator();
            String secret = secretGenerator.generate();
            client.setToken(secret);
            return client;
        }
        else{
            return null;
        }
    }

    @Override
    public boolean loginExist(String login) {
        return findByLogin(login) != null;
    }

    @Override
    public Client findByLogin(String login) {
        try {
            QueryBuilder<Client, Integer> queryBuilder = dao().queryBuilder();
            queryBuilder.where().eq(Client.COLUMN_LOGIN, login);
            PreparedQuery<Client> preparedQuery = queryBuilder.prepare();
            Client client = dao().queryForFirst(preparedQuery);
            return client;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("dao exception");
        }
    }

    @Override
    public Client authenticate(String token, LocalDate date) {
        if(!tokenExist(token)){
            throw new RuntimeException("token doesn't exist");
        }
        Client client = findByToken(token);
        if(date.isAfter(client.getDateOfExtermination())) {
            client.setToken(null);
            throw new RuntimeException("token is invalid. Please log in using login and password");
        }
        else{
            return client;
        }
    }

    @Override
    public boolean tokenExist(String token) {
        return findByToken(token) != null;
    }

    @Override
    public Client findByToken(String token) {
        try {
            QueryBuilder<Client, Integer> queryBuilder = dao().queryBuilder();
            queryBuilder.where().eq(Client.COLUMN_TOKEN, token);
            PreparedQuery<Client> preparedQuery = queryBuilder.prepare();
            Client client = dao().queryForFirst(preparedQuery);
            return client;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("dao exception");
        }
    }

    @Override
    public List<ModelPermission> access(Client client, Client target) {
        List<ModelPermission> modelPermissions = new ArrayList<>();
        if(client.getId() == target.getId()) {
            modelPermissions.add(ModelPermission.READ);
            modelPermissions.add(ModelPermission.CREATE);
            modelPermissions.add(ModelPermission.DELETE);
            modelPermissions.add(ModelPermission.UPDATE);
            return modelPermissions;
        }
        else {
            return modelPermissions;
        }
    }

    @Override
    public List<ModelPermission> access(Client client, Product target) {
        List<ModelPermission> modelPermissions = new ArrayList<>();
        modelPermissions.add(ModelPermission.READ);
        return modelPermissions;
    }

    @Override
    public List<ModelPermission> access(Client client, Order order) {
        List<ModelPermission> modelPermissions = new ArrayList<>();
        if(client != null) {
            modelPermissions.add(ModelPermission.CREATE);
        }

        if(client == order.getClient()) {
            modelPermissions.add(ModelPermission.READ);
            modelPermissions.add(ModelPermission.CREATE);
        }
        return modelPermissions;
    }
}
