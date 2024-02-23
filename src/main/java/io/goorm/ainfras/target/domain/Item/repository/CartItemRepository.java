package io.goorm.ainfras.target.domain.Item.repository;

import io.goorm.ainfras.target.domain.Item.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.user.id = :userId")
    void deleteByUserId(Long userId);
}
