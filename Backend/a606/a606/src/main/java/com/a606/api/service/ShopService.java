package com.a606.api.service;

import com.a606.api.dto.InventoryDto;
import com.a606.api.dto.ItemDto;
import com.a606.api.dto.MyNFTDto;
import com.a606.db.entity.Item;
import com.a606.db.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface ShopService {

    Long checkDuplicated(long head, long eyes, long mouth, long hands, long fashion);

    boolean checkItems(User user, List<Long> itemIds);

    @Transactional
    Long createNFT(User user, List<Long> itemIds, String tokenURI, String name) throws Exception;
}
