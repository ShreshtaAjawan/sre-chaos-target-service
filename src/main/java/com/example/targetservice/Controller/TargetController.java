package com.example.targetservice.Controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


@RestController
@RequestMapping("/api/v1/error")
public class TargetController {

    private static final Logger log = LoggerFactory.getLogger(TargetController.class);

    @GetMapping("/null-pointer")
    public ResponseEntity<String> triggerNullPointer(){
        try {
            String userToken = null;
            userToken.toUpperCase(); // This will explicitly crash with a NullPointerException
        } catch (NullPointerException e) {
            log.error("Authentication mapping failed during JWT claims parsing", e);
            return ResponseEntity.internalServerError().body("Null pointer error logged.");
        }
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/db-timeout")
    public ResponseEntity<String> triggerDbTimeout(){
        try{
            throw new SQLException("HikariPool-1 - Connection is not available, request timed out after 30000ms.");
        }catch (SQLException e){
            log.error("Database connection failure encountered while fetching user metrics",e);
            return ResponseEntity.internalServerError().body("Database error logged");
        }
    }

    @GetMapping("/network-failure")
    public ResponseEntity<String> triggerNetworkFailure(){
        try{
            throw new RuntimeException("I/O error on GET request for \"https://paymentgateway.com\": Connection refused");
        }catch (RuntimeException e){
            log.error("Downstream integration thread blocked permanently",e);
            return ResponseEntity.internalServerError().body("Network timeout logged");
        }
    }

}
