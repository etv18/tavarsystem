package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.item.CreateItemRequest;
import com.tavarlabs.tavarsystem.dtos.item.ItemResponse;
import com.tavarlabs.tavarsystem.dtos.item.UpdateItemRequest;
import com.tavarlabs.tavarsystem.dtos.message.SimpleMessage;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    ItemResponse createItem(CreateItemRequest request);
    List<ItemResponse> getAll();
    ItemResponse updateItem(UpdateItemRequest request);
    SimpleMessage deleteItem(UUID publicId);
    ItemResponse getSingleItem(UUID publicId);
}
