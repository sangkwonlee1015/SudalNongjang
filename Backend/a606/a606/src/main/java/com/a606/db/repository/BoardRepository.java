package com.a606.db.repository;

import com.a606.db.entity.Board;
import com.a606.db.entity.NFT;
import com.a606.db.entity.User;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByNftAndStartBeforeAndEndAfter(NFT nft, LocalDateTime time1, LocalDateTime time2);

    Optional<Board> findByNftAndStart(NFT nft, LocalDateTime ofInstant);

    List<Board> findByUserAndStartBeforeAndEndAfter(User user, LocalDateTime time1, LocalDateTime time2);
}
