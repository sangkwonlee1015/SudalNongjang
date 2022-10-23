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
public class NFT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id = null;

    @Column(nullable = false)
    private String tokenId;


    @Column(nullable = false)
    private boolean isSaled;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private String tokenURI;

    @Column(nullable = false)
    private String owner;

    //OneToMany
    //Likes
    @OneToMany(mappedBy = "nft")
    private List<Likes> likes = new ArrayList<>();
    //Appeal
    @OneToMany(mappedBy = "nft")
    private List<Appeal> appeals = new ArrayList<>();
    //BidLog
    @OneToMany(mappedBy = "nft")
    private List<BidLog> bidLogs = new ArrayList<>();
    //Board
    @OneToMany(mappedBy = "nft")
    private List<Board> boards = new ArrayList<>();

}
