package com.a606.api.service;

import com.a606.api.dto.InventoryDto;
import com.a606.db.entity.Item;
import com.a606.db.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    void insertNewItem(User user, Item item);

    List<InventoryDto> getInventory(User user);
}
