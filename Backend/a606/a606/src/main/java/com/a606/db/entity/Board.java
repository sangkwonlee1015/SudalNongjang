package com.a606.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id = null;

    //user_id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //nft_id
    @ManyToOne
    @JoinColumn(name = "nft_id")
    private NFT nft;
    //start
    @Column(nullable = false)
    private LocalDateTime start;
    //end
    @Column(nullable = false)
    private LocalDateTime end;
    //first_price
    @Column(nullable = false)
    private double first_price;

    @OneToMany(mappedBy = "board")
    private List<BidLog> bidLogs = new ArrayList<>();

}
