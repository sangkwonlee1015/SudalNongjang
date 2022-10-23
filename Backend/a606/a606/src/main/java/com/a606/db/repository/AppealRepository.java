package com.a606.db.repository;

import com.a606.db.entity.Appeal;
import com.a606.db.entity.NFT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {
    List<Appeal> findAllByNft(NFT nft);
}
