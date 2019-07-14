package com.hanfak;

import java.util.List;

public class HanBasket {

  private final ItemPricesRepository repository;

  public HanBasket(ItemPricesRepository repository) {
    this.repository = repository;
  }

  public Double total(List<String> basketItems) {
    return basketItems.stream()
            .mapToDouble(repository::findPrice)
            .sum();
  }
}
