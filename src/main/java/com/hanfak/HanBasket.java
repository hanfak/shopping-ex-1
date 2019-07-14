package com.hanfak;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class HanBasket {

  private final ItemPricesRepository repository;

  public HanBasket(ItemPricesRepository repository) {
    this.repository = repository;
  }

  public BigDecimal total(List<String> basketItems) {
    BigDecimal price = basketItems.stream()
            .mapToDouble(repository::findPrice)
            .mapToObj(BigDecimal::new)
            .reduce(BigDecimal.ZERO, BigDecimal::add);


    return price.setScale(2, RoundingMode.HALF_EVEN);

  }
}
