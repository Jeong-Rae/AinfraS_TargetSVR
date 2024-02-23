package io.goorm.ainfras.target.domain.Item.converter;

import io.goorm.ainfras.target.domain.Item.domain.Item;
import io.goorm.ainfras.target.domain.Item.dto.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemConverter {
    public Item convert(ItemDTO itemDTO) {
        return Item.builder()
                .name(itemDTO.name())
                .price(itemDTO.price())
                .build();
    }

    public ItemDTO convert(Item item) {
        return new ItemDTO(item.getName(), item.getPrice(), item.getCode());
    }
}
