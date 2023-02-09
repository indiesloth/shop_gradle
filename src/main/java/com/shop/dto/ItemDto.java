package com.shop.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDto {

  private Long id;
  private String itemNm;
  private Integer price;
  private String itemDetail;
  private String sellStatCd;
  private LocalDateTime regTime;
  private LocalDateTime updateTime;
}
