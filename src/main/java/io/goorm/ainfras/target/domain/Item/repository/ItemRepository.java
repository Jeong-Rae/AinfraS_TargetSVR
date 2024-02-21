package io.goorm.ainfras.target.domain.Item.repository;

import io.goorm.ainfras.target.domain.Item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
