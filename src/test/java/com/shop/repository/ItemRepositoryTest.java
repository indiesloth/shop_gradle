package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

  @Autowired
  ItemRepository itemRepository;

  @Test
  @DisplayName("상품 저장 테스트")
  public void createItemTest() {
    Item item = new Item();
    item.setItemNm("테스트 상품");
    item.setPrice(10000);
    item.setItemDetail("테스트 상품 상세 설명");
    item.setItemSellStatus(ItemSellStatus.SELL);
    item.setStockNumber(100);

    Item savedItem = itemRepository.save(item);
    System.out.println(":::::" + savedItem);
  }

  public void createItemList() {
    IntStream.rangeClosed(1, 10).forEach(idx -> itemRepository.save(
        Item.builder()
            .itemNm("테스트 상품" + idx)
            .price(10000 + idx)
            .itemDetail("테스트 상품 상세 설명" + idx)
            .itemSellStatus(ItemSellStatus.SELL)
            .stockNumber(100)
            .build()
    ));
  }

  @Test
  @DisplayName("상품명 조회 테스트")
  public void findByItemNmTest() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
    for (Item item : itemList) {
      System.out.println(item.toString());
    }
  }
}