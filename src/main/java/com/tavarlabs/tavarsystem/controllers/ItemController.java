package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.item.CreateItemRequest;
import com.tavarlabs.tavarsystem.dtos.item.ItemResponse;
import com.tavarlabs.tavarsystem.dtos.item.UpdateItemRequest;
import com.tavarlabs.tavarsystem.dtos.message.SimpleMessage;
import com.tavarlabs.tavarsystem.service.ItemService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{publicId}")
    public ItemResponse getSingle(
            @PathVariable("publicId") UUID publicId
    ){
        return itemService.getSingleItem(publicId);
    }

    @PutMapping
    public ItemResponse update(
            @Valid @RequestBody UpdateItemRequest request
    ) {
        return itemService.updateItem(request);
    }

    @DeleteMapping("/{publicId}")
    public SimpleMessage delete(
            @PathVariable("publicId") UUID publicId
    ) {
        return itemService.deleteItem(publicId);
    }
}
