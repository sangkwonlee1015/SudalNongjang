package com.a606.api.service;

import com.a606.api.dto.InventoryDto;
import com.a606.db.entity.Inventory;
import com.a606.db.entity.Item;
import com.a606.db.entity.User;
import com.a606.db.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    InventoryRepository inventoryRepository;


    @Override
    public void insertNewItem(User user, Item item) {
        if(inventoryRepository.findByUserAndItem(user, item).isPresent()) {
            Inventory inventory = inventoryRepository.findByUserAndItem(user, item).get();
            inventory.setHowMany(inventory.getHowMany() + 1);
            inventoryRepository.save(inventory);
        } else {
            Inventory inventory = new Inventory();
            inventory.setUser(user);
            inventory.setItem(item);
            inventory.setHowMany(1);
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public List<InventoryDto> getInventory(User user) {
        List<Inventory> inventories = inventoryRepository.findByUser(user).get();
        List<InventoryDto> list = new ArrayList<>();
        for(Inventory i : inventories) {
            InventoryDto inventoryDto = new InventoryDto();

            inventoryDto.setId(i.getId());
            inventoryDto.setUserId(user.getId());
            inventoryDto.setItemId(i.getItem().getId());
            inventoryDto.setType(i.getItem().getType());
            inventoryDto.setNumber(i.getItem().getNumber());
            inventoryDto.setRgb(i.getItem().getRgb());
            inventoryDto.setRare(i.getItem().getRare());
            inventoryDto.setHowMany(i.getHowMany());

            list.add(inventoryDto);
        }


        return list;
    }
}
