package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.item.CreateItemRequest;
import com.tavarlabs.tavarsystem.dtos.item.ItemResponse;
import com.tavarlabs.tavarsystem.entity.Item;
import com.tavarlabs.tavarsystem.service.ItemService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/item")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemResponse create(
            @Valid @RequestBody CreateItemRequest request
    ) {
        return itemService.createItem(request);
    }

    @GetMapping
    public List<ItemResponse> getAll(){
        return itemService.getAll();
    }
}
