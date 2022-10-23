package com.a606.api.service;

import com.a606.api.dto.ItemDto;
import com.a606.db.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    void createItem(int type, int number, int rgb, int rare);
    List<ItemDto> getAllItems();
    void updateItem(long itemId, int type, int number, int rgb, int rare);
    void deleteItem(long itemId);
    ItemDto getRandomItem(int type);

    Item getItem(long itemId);
}
