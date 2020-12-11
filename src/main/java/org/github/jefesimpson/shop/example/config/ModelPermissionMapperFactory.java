package org.github.jefesimpson.shop.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.jefesimpson.shop.example.model.ModelPermission;
import com.fasterxml.jackson.databind.Module;

import java.util.Map;

public class ModelPermissionMapperFactory implements MapperFactory {
    private Map<ModelPermission, Module> permission;

    public ModelPermissionMapperFactory() {}

    public ModelPermissionMapperFactory(Map<ModelPermission, Module> permission) {
        this.permission = permission;
    }

    public Map<ModelPermission, Module> getPermission() {
        return permission;
    }

    public void setPermission(Map<ModelPermission, Module> permission) {
        this.permission = permission;
    }

    @Override
    public ObjectMapper objectMapper(ModelPermission modelPermission) {
        ObjectMapper mapper = new ObjectMapper();
        Module module = permission.get(modelPermission);
        mapper.registerModule(module);
        return mapper;
    }
}
