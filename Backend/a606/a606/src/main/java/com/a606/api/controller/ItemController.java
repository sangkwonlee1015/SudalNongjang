package com.a606.api.controller;

import com.a606.api.dto.InventoryDto;
import com.a606.api.dto.ItemDto;
import com.a606.api.service.InventoryService;
import com.a606.api.service.ItemService;
import com.a606.common.util.SudalUserDetails;
import com.a606.db.entity.Item;
import com.a606.db.entity.User;
import com.a606.db.repository.ItemRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@Api(value = "아이템 API", tags = {"item"})
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ItemController {
    @Autowired
    private final ItemService itemService;
    @Autowired
    private final InventoryService inventoryService;

    //C
    @PostMapping("item")
    public void createItem(@RequestBody ItemDto.createItemRequest createItemRequest) {
        itemService.createItem(createItemRequest.getType(), createItemRequest.getNumber(), createItemRequest.getRgb(), createItemRequest.getRare());
    }
    //R
    @GetMapping("item")
    public ResponseEntity<List<ItemDto>> readItem() {
        List<ItemDto> list = itemService.getAllItems();
        return new ResponseEntity<List<ItemDto>>(list, HttpStatus.OK);
    }
    //U
    @PutMapping("item")
    public void updateItem(@RequestBody ItemDto itemDto) {
        itemService.updateItem(itemDto.getId(), itemDto.getType(), itemDto.getNumber(), itemDto.getRgb(), itemDto.getRare());
    }
    //D
    @DeleteMapping("item")
    public void deleteItem(@RequestBody ItemDto itemDto) {
        itemService.deleteItem(itemDto.getId());
    }

    @GetMapping("item/{type}")
    @ApiOperation(value = "아이템 랜덤 뽑기", notes = "레어도 1: 50% 2: 40% 3. 10%")
    public ResponseEntity<ItemDto> getRandomItem(@ApiIgnore Authentication authentication, @PathVariable int type) {
        if (authentication == null) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        User user = ((SudalUserDetails) authentication.getDetails()).getUser();
        ItemDto itemDto = itemService.getRandomItem(type);
        inventoryService.insertNewItem(user, itemService.getItem(itemDto.getId()));

        return new ResponseEntity<ItemDto>(itemDto, HttpStatus.OK);
    }

    @GetMapping("item/inventory")
    public ResponseEntity<List<InventoryDto>> getInventory(@ApiIgnore Authentication authentication) {
        if (authentication == null) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        User user = ((SudalUserDetails) authentication.getDetails()).getUser();

        return new ResponseEntity<List<InventoryDto>>(inventoryService.getInventory(user), HttpStatus.OK);
    }


//    @GetMapping("item/test/inputRandom10Items")
//    public void inputRandom10Items() {
//        for(int i = 0; i < 10; i++) {
//            int type = getRandomInt(1, 5);
//            int number = getRandomInt(1, 100);
//            int rgb = getRandomInt(1, 100);
//            int rare = getRandomInt(1, 3);
//            itemService.createItem(type, number, rgb, rare);
//        }
//    }
//
//    @GetMapping("item/deleteAll")
//    public void deleteAll() {
//        List<ItemDto> itemDtos = itemService.getAllItems();
//        for(ItemDto i : itemDtos) {
//            itemService.deleteItem(i.getId());
//        }
//    }

    public static int getRandomInt(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }
}
