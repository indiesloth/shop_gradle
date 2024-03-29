package com.shop.controller;

import com.shop.dto.ItemDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/thymeleaf")
@Controller
public class ThymeleafController {

  @GetMapping(value = "/ex01")
  public String thymeleafExample01(Model model) {
    model.addAttribute("data", "타임리프 예제 입니다.");
    return "thymeleafEx/thymeleafEx01";
  }

  @GetMapping(value = "/ex02")
  public String thymeleafExample02(Model model) {
    ItemDto itemDto = new ItemDto();
    itemDto.setItemDetail("상품 상세 설명");
    itemDto.setItemNm("테스트 상품1");
    itemDto.setPrice(10000);
    itemDto.setRegTime(LocalDateTime.now());

    model.addAttribute("itemDto", itemDto);
    return "thymeleafEx/thymeleafEx02";
  }

  public List<ItemDto> createItemDtoList() {
    List<ItemDto> itemDtoList = new ArrayList<>();
    IntStream.rangeClosed(1, 10).forEach(idx -> {
      itemDtoList.add(ItemDto.builder()
          .itemDetail("상품 상세 설명" + idx)
          .itemNm("테스트 상품" + idx)
          .price(1000 * idx)
          .regTime(LocalDateTime.now())
          .build());
    });
    return itemDtoList;
  }

  @GetMapping(value = "/ex03")
  public String thymeleafExample03(Model model) {
    List<ItemDto> itemDtoList = new ArrayList<>();
    model.addAttribute("itemDtoList", createItemDtoList());
    return "thymeleafEx/thymeleafEx03";
  }

  @GetMapping(value = "/ex04")
  public String thymeleafExample04(Model model) {
    List<ItemDto> itemDtoList = new ArrayList<>();
    model.addAttribute("itemDtoList", createItemDtoList());
    return "thymeleafEx/thymeleafEx04";
  }

  @GetMapping(value = "/ex05")
  public String thymeleafExample05(Model model) {
    return "thymeleafEx/thymeleafEx05";
  }

  @GetMapping(value = "/ex06")
  public String thymeleafExample06(Model model, String param1, String param2) {
    model.addAttribute("param1", param1);
    model.addAttribute("param2", param2);
    return "thymeleafEx/thymeleafEx06";
  }

  @GetMapping(value = "/ex07")
  public String thymeleafExample07(Model model) {
    return "thymeleafEx/thymeleafEx07";
  }
}
