package org.github.jefesimpson.shop.example.service;

import org.github.jefesimpson.shop.example.model.Client;
import org.github.jefesimpson.shop.example.model.ModelPermission;
import org.github.jefesimpson.shop.example.model.Order;
import org.github.jefesimpson.shop.example.model.Product;

import java.time.LocalDate;
import java.util.List;

public interface ClientService extends Service<Client> {
    Client authenticate(String login, String password);
    boolean loginExist(String login);
    Client findByLogin(String login);
    Client authenticate(String token, LocalDate date);
    boolean tokenExist(String token);
    Client findByToken(String token);
    List<ModelPermission> access(Client client, Client target);
    List<ModelPermission> access(Client client, Product target);
    List<ModelPermission> access(Client client, Order order);

}
