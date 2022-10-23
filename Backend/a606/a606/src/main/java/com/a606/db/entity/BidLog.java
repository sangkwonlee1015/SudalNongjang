package com.a606.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
public class BidLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id = null;

    //date
    @Column(nullable = false)
    private LocalDateTime date;

    //price
    @Column(nullable = false)
    private double price;

    //nftId
    @ManyToOne
    @JoinColumn(name = "nft_id")
    private NFT nft;

    //userId
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //boardId
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
