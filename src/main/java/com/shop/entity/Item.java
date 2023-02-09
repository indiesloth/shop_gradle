package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item extends BaseEntity {

  @Id
  @Column(name = "item_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Comment("상품 코드")
  private Long id;
  @Column(nullable = false, length = 50)
  @Comment("상품명")
  private String itemNm;
  @Column(name = "price", nullable = false)
  @Comment("가격")
  private int price;
  @Column(nullable = false)
  @Comment("재고수량")
  private int stockNumber;
  @Lob
  @Comment("상품 상세 설명")
  private String itemDetail;
  @Enumerated(EnumType.STRING)
  @Comment("상품 판매 상태")
  private ItemSellStatus itemSellStatus;
}
