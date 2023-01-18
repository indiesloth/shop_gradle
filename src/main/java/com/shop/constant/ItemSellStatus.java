package com.shop.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum ItemSellStatus {
  SELL("판매중"),
  SOLD_OUT("매진");
  private String desc;
}
