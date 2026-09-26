package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.utils.AppKeywords;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/test")
public class AppTestController {

    @PostMapping("/general")
    public ResponseEntity<?> viewGeneral(){
        return ResponseEntity.ok(Map.of("message", "Open to the public"));
    }
}
