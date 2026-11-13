package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.item.CreateItemRequest;
import com.tavarlabs.tavarsystem.dtos.item.ItemResponse;

import java.util.List;

public interface ItemService {
    ItemResponse createItem(CreateItemRequest request);
    List<ItemResponse> getAll();
}
