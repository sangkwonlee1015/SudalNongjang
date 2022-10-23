package com.a606.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
public class IssuedAvatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id = null;

    //head
    @Column(nullable = false)
    private Long head;
    //eyes
    @Column(nullable = false)
    private Long eyes;
    //mouth
    @Column(nullable = false)
    private Long mouth;
    //hands
    @Column(nullable = false)
    private Long hands;
    //fashion
    @Column(nullable = false)
    private Long fashion;
    @Column(nullable = false)
    private Long nftId;
    @Column(nullable = false)
    private Long firstUserId;
}
