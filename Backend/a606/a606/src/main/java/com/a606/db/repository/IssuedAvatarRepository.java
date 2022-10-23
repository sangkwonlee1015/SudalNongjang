package com.a606.db.repository;

import com.a606.db.entity.IssuedAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssuedAvatarRepository extends JpaRepository<IssuedAvatar, Long> {

    Optional<IssuedAvatar> findByHeadAndEyesAndMouthAndHandsAndFashion(Long head, Long eyes, Long mouth, Long hands, Long fashion);
}
