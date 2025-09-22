package com.isf.guatemala.controller;

import com.isf.guatemala.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {
    
    @Autowired
    private DataSource dataSource;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<Map<String, Object>>> test() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Backend ISF Guatemala funcionando correctamente");
        data.put("status", "OK");
        data.put("version", "1.0.0");
        
        // Verificar conexión a base de datos
        try (Connection connection = dataSource.getConnection()) {
            data.put("database", "Conectado correctamente");
        } catch (Exception e) {
            data.put("database", "Error de conexión: " + e.getMessage());
        }
        
        return ResponseEntity.ok(ApiResponse.success("Sistema funcionando correctamente", data));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Sistema ISF Guatemala activo"));
    }
}