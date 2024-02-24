package com.rdi.ecommerce.dto;

import com.rdi.ecommerce.data.model.Inventory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class StoreResponse {
    private Long id;
    private InventoryResponse inventory;
}
