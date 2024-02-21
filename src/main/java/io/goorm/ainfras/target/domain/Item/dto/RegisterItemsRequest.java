package io.goorm.ainfras.target.domain.Item.dto;

import java.util.List;

public record RegisterItemsRequest(
        String seller,
        List<ItemDTO> items
) {
}
