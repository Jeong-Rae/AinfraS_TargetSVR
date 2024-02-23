package io.goorm.ainfras.target.domain.Item.domain;


import io.goorm.ainfras.target.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table
public class Item extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private Integer price;
    private String seller;

    @Builder
    public Item(String code, String name, Integer price, String seller) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.seller = seller;
    }

    public Item() {
    }

    public void generateCode() {
        this.code = String.format("%03d", this.id);
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
