package com.hanfak;

import java.math.BigDecimal;
import java.util.List;

import static java.math.RoundingMode.HALF_EVEN;
import static java.util.stream.Collectors.partitioningBy;

public class HanBasket {

  private final ItemPricesRepository repository;

  public HanBasket(ItemPricesRepository repository) {
    this.repository = repository;
  }

  public BigDecimal total(List<String> basketItems) {

    double subTotalForMelons = numberOfMelonsToPayFor(basketItems) * repository.findPrice("Melon");

    BigDecimal price = basketItems.stream()
            .filter(item -> !item.equals("Melon"))
            .mapToDouble(repository::findPrice)
            .mapToObj(BigDecimal::new)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return price.setScale(2, HALF_EVEN)
            .add(BigDecimal.valueOf(subTotalForMelons));
  }

  private int numberOfMelonsToPayFor(List<String> items) {
    return (int) Math.ceil(numberOfMelons(items) / 2.0);
  }

  private int numberOfMelons(List<String> basketItems) {
    return basketItems.stream()
            .collect(partitioningBy(item -> item.equals("Melon")))
            .get(true)
            .size();
  }
}
