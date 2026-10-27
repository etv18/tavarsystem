package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.auth.ActionResponse;
import com.tavarlabs.tavarsystem.dtos.auth.CreateActionRequest;

import java.util.List;

public interface ActionService {
    ActionResponse createAction(CreateActionRequest request);
    List<String> findAll();
}
