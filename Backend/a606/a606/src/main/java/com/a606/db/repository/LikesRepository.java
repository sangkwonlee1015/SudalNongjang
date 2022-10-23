package com.a606.db.repository;

import com.a606.db.entity.Likes;
import com.a606.db.entity.NFT;
import com.a606.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {


    Optional<Likes> findByUserAndNft(User user, NFT nft);
}
