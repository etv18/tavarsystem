package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.item.CreateItemRequest;
import com.tavarlabs.tavarsystem.dtos.item.ItemResponse;
import com.tavarlabs.tavarsystem.entity.Item;
import com.tavarlabs.tavarsystem.mappers.ItemMapper;
import com.tavarlabs.tavarsystem.repository.CategoryRepository;
import com.tavarlabs.tavarsystem.repository.ItemRepository;
import com.tavarlabs.tavarsystem.repository.TaxTypeRepository;
import com.tavarlabs.tavarsystem.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepo;
    private final CategoryRepository categoryRepo;
    private final ItemMapper itemMapper;
    private final TaxTypeRepository taxTypeRepo;

    @Transactional
    @Override
    public ItemResponse createItem(CreateItemRequest request) {
        Item item = Item.builder()
                .code(request.code())
                .name(request.name())
                .description(request.description())
                .category(categoryRepo.findByName(request.category()))
                .taxType(taxTypeRepo.findByName(request.taxType()))
                .unitPrice(request.unitPrice())
                .unitOfMeasure(request.unitOfMeasure())
                .quantity(request.quantity())
                .build();
        Item savedItem = itemRepo.save(item);
        return itemMapper.toResponse(item);
    }

    @Override
    public List<ItemResponse> getAll() {
        return itemRepo.findAll().stream()
                .map(itemMapper::toResponse)
                .toList();
    }
}
