package com.a606.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id = null;

    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int rgb;

    @Column(nullable = false)
    private int rare;

    @OneToMany(mappedBy = "item")
    private List<Inventory> inventories = new ArrayList<>();
}
