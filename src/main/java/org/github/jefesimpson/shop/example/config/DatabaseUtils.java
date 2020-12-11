package org.github.jefesimpson.shop.example.config;

import com.j256.ormlite.support.ConnectionSource;

public interface DatabaseUtils {
    String jdbcConnectionString();
    ConnectionSource connectionSource();
}
