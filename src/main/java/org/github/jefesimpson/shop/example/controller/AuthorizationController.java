package org.github.jefesimpson.shop.example.controller;

import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import org.github.jefesimpson.shop.example.model.Client;
import org.github.jefesimpson.shop.example.model.Employee;
import org.github.jefesimpson.shop.example.service.ClientService;
import org.github.jefesimpson.shop.example.service.EmployeeService;

import java.time.LocalDate;
import java.util.Map;

public interface AuthorizationController<T> extends Controller<T> {
    ClientService clientService();
    EmployeeService employeeService();

    default Client clientSender(Context context) {
        if(!context.basicAuthCredentialsExist()) {
            Map<String, String> strings = context.headerMap();
            String authorization = strings.get("Authorization");
            String token = authorization.substring(SUBSTRING_INT, SUBSTRING_INT_END(authorization));
            if(token != null){
                return clientService().authenticate(token, LocalDate.now());
            }
            else{
                return null;
            }
        }
        String login = context.basicAuthCredentials().getUsername();
        String password = context.basicAuthCredentials().getPassword();
        return clientService().authenticate(login, password);
    }

    default Client clientSenderOrThrowUnauthorized(Context context) {
        Client client = clientSender(context);
        if(client == null) {
            throw new UnauthorizedResponse();
        }
        return client;
    }

    default Employee employeeSender(Context context) {
        if(!context.basicAuthCredentialsExist()) {
            Map<String, String> strings = context.headerMap();
            String authorization = strings.get("Authorization");
            String token = authorization.substring(SUBSTRING_INT, SUBSTRING_INT_END(authorization));
            if(token != null){
                return employeeService().authenticate(token, LocalDate.now());
            }
            else{
                return null;
            }
        }
        String login = context.basicAuthCredentials().getUsername();
        String password = context.basicAuthCredentials().getPassword();
        return employeeService().authenticate(login, password);
    }

    default Employee employeeSenderOrThrowUnauthorized(Context context) {
        Employee employee = employeeSender(context);
        if(employee == null) {
            throw new UnauthorizedResponse();
        }
        return employee;
    }

    public final static int SUBSTRING_INT = 7;
    public static int SUBSTRING_INT_END(String str){
        return  str.length();
    }

}
