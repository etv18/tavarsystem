package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.utils.AppKeywords;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/test")
public class AppTestController {

    @GetMapping("/general")
    public ResponseEntity<?> viewGeneral(){
       try{
           return ResponseEntity.ok(
                   Map.of(
                           "message", "Open to the public",
                           "timestamp", LocalDateTime.now()
                   )
           );
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @GetMapping("/restricted")
    public ResponseEntity<?> viewRestricted(){
        try {
            return ResponseEntity.ok(
                    Map.of(
                            "message", "Only nice people can see it...",
                            "timestamp", LocalDateTime.now()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
