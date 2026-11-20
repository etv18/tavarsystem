package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.item.CreateItemRequest;
import com.tavarlabs.tavarsystem.dtos.item.ItemResponse;
import com.tavarlabs.tavarsystem.dtos.item.UpdateItemRequest;
import com.tavarlabs.tavarsystem.dtos.message.SimpleMessage;
import com.tavarlabs.tavarsystem.entity.Item;
import com.tavarlabs.tavarsystem.mappers.ItemMapper;
import com.tavarlabs.tavarsystem.repository.CategoryRepository;
import com.tavarlabs.tavarsystem.repository.ItemRepository;
import com.tavarlabs.tavarsystem.repository.TaxTypeRepository;
import com.tavarlabs.tavarsystem.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
        return itemMapper.toResponse(itemRepo.save(item));
    }

    @Override
    public List<ItemResponse> getAll() {
        return itemRepo.findAllUndeleted().stream()
                .map(itemMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public ItemResponse updateItem(UpdateItemRequest request) {
        Item item = findSingleItemByCode(request.code());

        item.setCode(request.code());
        item.setName(request.name());
        item.setDescription(request.description());
        item.setCategory(categoryRepo.findByName(request.name()));
        item.setUnitPrice(request.unitPrice());
        item.setTaxType(taxTypeRepo.findByName(request.name()));
        item.setUnitOfMeasure(request.unitOfMeasure());
        item.setQuantity(request.quantity());
        item.setActive(request.active());

        return itemMapper.toResponse(itemRepo.save(item));
    }

    @Transactional
    @Override
    public SimpleMessage deleteItem(UUID publicId) {
        Item item = itemRepo.findUndeletedByPublicId(publicId).orElseThrow(
                () -> new EntityNotFoundException("Item was not found. Public id: " + publicId)
        );
        item.setDeleted(true);
        itemRepo.save(item);
        return new SimpleMessage("Deleted successfully");
    }

    @Override
    public ItemResponse getSingleItem(String code) {
        return itemMapper.toResponse(findSingleItemByCode(code));
    }

    private Item findSingleItemByCode(String code){
        return itemRepo.findUndeletedByCode(code).orElseThrow(
                () -> new EntityNotFoundException("Item was not found. Code: " + code)
        );
    }
}
