package com.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

  @Autowired
  ItemRepository itemRepository;

  @PersistenceContext
  EntityManager em;

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

  public void itemListPrintln(List<Item> itemList) {
    System.out.println("==================================");
    for (Item item : itemList) {
      System.out.println(item.toString());
    }
    System.out.println("==================================");
  }

  @Test
  @DisplayName("상품명 조회 테스트")
  public void findByItemNmTest() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
    itemListPrintln(itemList);
  }

  @Test
  @DisplayName("상품명, 상품상세설명 or 테스트")
  public void findByItemNmOrItemDetailTest() {
    this.createItemList();
    List<Item> itemList =
        itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
    itemListPrintln(itemList);
  }

  @Test
  @DisplayName("가격 LessThan 테스트")
  public void findByPriceLessThanTest() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByPriceLessThan(10005);
    itemListPrintln(itemList);
  }

  @Test
  @DisplayName("가격 내림차순 조회 테스트")
  public void findByPriceLessThanOrderByPriceDesc() {
    this.createItemList();
    List<Item> itemList =
        itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
    itemListPrintln(itemList);
  }

  @Test
  @DisplayName("@Query 를 이용한 상품 조회 테스트")
  public void findByItemDetailTest() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
    itemListPrintln(itemList);
  }

  @Test
  @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
  public void findByItemDetailByNative() {
    this.createItemList();
    List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
    itemListPrintln(itemList);
  }

  @Test
  @DisplayName("Querydsl 조회 테스트1")
  public void queryDslTest() {
    this.createItemList();
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    QItem qItem = QItem.item;
    JPAQuery<Item> query = queryFactory.selectFrom(qItem)
        .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
        .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
        .orderBy(qItem.price.desc());

    List<Item> itemList = query.fetch();

    itemListPrintln(itemList);
  }

  public void createItemList2() {
    IntStream.rangeClosed(1, 5).forEach(idx -> itemRepository.save(
        Item.builder()
            .itemNm("테스트 상품" + idx)
            .price(10000 + idx)
            .itemDetail("테스트 상품 상세 설명" + idx)
            .itemSellStatus(ItemSellStatus.SELL)
            .stockNumber(100)
            .build()));

    IntStream.rangeClosed(6, 10).forEach(idx -> itemRepository.save(
        Item.builder()
            .itemNm("테스트 상품" + idx)
            .price(10000 + idx)
            .itemDetail("테스트 상품 상세 설명" + idx)
            .itemSellStatus(ItemSellStatus.SOLD_OUT)
            .stockNumber(0)
            .build()));
  }

  @Test
  @DisplayName("상품 Querydsl 조회 테스트2")
  public void queryDslTest2() {
    this.createItemList2();

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QItem item = QItem.item;
    String itemDetail = "테스트 상품 상세 설명";
    int price = 10003;
    String itemSellStat = "SELL";

    booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
    booleanBuilder.and(item.price.gt(price));

    if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
      booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
    }

    Pageable pageable = PageRequest.of(0, 5);
    Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
    System.out.println("total elements : " + itemPagingResult.getTotalElements());

    List<Item> resultItemList = itemPagingResult.getContent();
    itemListPrintln(resultItemList);
  }

  @Test
  @DisplayName("상품 Querydsl 조회 테스트3")
  public void specTest() {
    this.createItemList2();
    Item item = new Item();
    String itemDetail = "테스트 상품 상세 설명";
    int price = 10003;
    String itemSellStat = "SELL";

    Pageable pageable = PageRequest.of(0, 5);
    Specification<Item> spec = (root, query, criteriaBuilder) -> null;

    if (StringUtils.isEmpty(itemDetail)) {
      spec = spec.and(
          (root, query, builder) -> builder.and());
    }

    List<Item> itemPagingResult = itemRepository.findAll(spec, pageable).stream()
        .collect(Collectors.toList());

//    System.out.println("total elements : " + itemPagingResult.getTotalElements());
    itemListPrintln(itemPagingResult);

  }
}