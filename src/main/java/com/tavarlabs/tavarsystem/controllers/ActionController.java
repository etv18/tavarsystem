package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.auth.ActionResponse;
import com.tavarlabs.tavarsystem.dtos.auth.CreateActionRequest;
import com.tavarlabs.tavarsystem.service.ActionService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/action")
public class ActionController {
    private final ActionService actionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActionResponse create(@Valid @RequestBody CreateActionRequest request) {
        return actionService.createAction(request);
    }

    @GetMapping
    public List<String> findAll() {
        return actionService.findAll();
    }
}
