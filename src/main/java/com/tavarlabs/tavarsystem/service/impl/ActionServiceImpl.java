package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.auth.ActionResponse;
import com.tavarlabs.tavarsystem.dtos.auth.CreateActionRequest;
import com.tavarlabs.tavarsystem.entity.Action;
import com.tavarlabs.tavarsystem.repository.ActionRepository;
import com.tavarlabs.tavarsystem.service.ActionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepo;

    @Override
    @Transactional
    public ActionResponse createAction(CreateActionRequest request) {
        String code = request.code().trim().toUpperCase().replace(" ", "_");

        if (actionRepo.existsByCode(code)) {
            throw new DataIntegrityViolationException("Action already exists: " + code.toUpperCase());
        }

        Action saved = actionRepo.save(new Action(code));
        return new ActionResponse(saved.getCode());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAll() {
        return actionRepo.findAllCodes();
    }
}
