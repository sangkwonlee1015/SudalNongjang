package com.a606.db.repository;

import com.a606.api.dto.MyNFTDto;
import com.a606.db.entity.NFT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NFTRepository extends JpaRepository<NFT, Long> {
    Optional<NFT> findById(long nftId);

    Optional<NFT> findByTokenId(String tokenId);

    Page<NFT> findAllByIsSaled(boolean isSaled, Pageable pageable);

    long countByIsSaled(boolean isSaled);

    List<NFT> findAllByOwner(String wallet);
}
