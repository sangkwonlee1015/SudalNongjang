package com.a606.api.service;

import com.a606.api.dto.ItemDto;
import com.a606.db.entity.Item;
import com.a606.db.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    ItemRepository itemRepository;

    @Override
    public void createItem(int type, int number, int rgb, int rare) {
        Item item = new Item();
        item.setType(type);
        item.setNumber(number);
        item.setRgb(rgb);
        item.setRare(rare);

        itemRepository.save(item);
    }

    @Override
    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> list = new ArrayList<>();
        for(Item i : items) {
            ItemDto itemDto = new ItemDto();
            itemDto.setId(i.getId());
            itemDto.setType(i.getType());
            itemDto.setNumber(i.getNumber());
            itemDto.setRgb(i.getRgb());
            itemDto.setRare(i.getRare());
            list.add(itemDto);
        }
        return list;
    }

    @Override
    public void updateItem(long itemId, int type, int number, int rgb, int rare) {
        Item item = itemRepository.findById(itemId).get();
        item.setType(type);
        item.setNumber(number);
        item.setRgb(rgb);
        item.setRare(rare);
        itemRepository.save(item);
    }

    @Override
    public void deleteItem(long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public ItemDto getRandomItem(int type) {
        List<Item> items = itemRepository.findByType(type);
        List<ItemDto> list1 = new ArrayList<>();
        List<ItemDto> list2 = new ArrayList<>();
        List<ItemDto> list3 = new ArrayList<>();
        for(Item i : items) {
            ItemDto itemDto = new ItemDto();
            itemDto.setId(i.getId());
            itemDto.setType(i.getType());
            itemDto.setNumber(i.getNumber());
            itemDto.setRgb(i.getRgb());
            itemDto.setRare(i.getRare());
            if(i.getRare() == 1) list1.add(itemDto);
            if(i.getRare() == 2) list2.add(itemDto);
            if(i.getRare() == 3) list3.add(itemDto);
        }
        int random = (int) (Math.random() * 10 + 1);
        if(1 <= random && random <= 5) {
            int randomItem = (int) (Math.random() * list1.size());
            return list1.get(randomItem);
        }
        else if(6 <= random && random <= 9) {
            int randomItem = (int) (Math.random() * list2.size());
            return list2.get(randomItem);
        }
        else {
            int randomItem = (int) (Math.random() * list3.size());
            return list3.get(randomItem);
        }
    }

    @Override
    public Item getItem(long itemId) {
        return itemRepository.findById(itemId).get();
    }
}
