package org.github.jefesimpson.shop.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.jefesimpson.shop.example.model.ModelPermission;

public interface MapperFactory {
    ObjectMapper objectMapper(ModelPermission modelPermission);
}
