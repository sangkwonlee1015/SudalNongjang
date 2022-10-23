package com.a606.db.repository;

import com.a606.db.entity.Inventory;
import com.a606.db.entity.Item;
import com.a606.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByUserAndItem(User user, Item item);
    Optional<List<Inventory>> findByUser(User user);
}
