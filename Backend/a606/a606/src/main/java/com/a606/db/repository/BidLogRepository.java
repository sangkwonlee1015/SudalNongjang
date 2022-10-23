package com.a606.db.repository;

import com.a606.db.entity.BidLog;
import com.a606.db.entity.Board;
import com.a606.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BidLogRepository extends JpaRepository<BidLog, Long> {
    Optional<BidLog> findByUserAndBoardAndDate(User user, Board board, LocalDateTime ofInstant);
}
