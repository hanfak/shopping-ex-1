package com.hanfak;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.util.stream.Collectors.partitioningBy;

public class HanBasket {

  private final ItemPricesRepository repository;

  public HanBasket(ItemPricesRepository repository) {
    this.repository = repository;
  }

  public BigDecimal total(List<String> basketItems) {

    double subTotalForMelons = getNumberOfPairsOfMelons(basketItems) * repository.findPrice("Melon");

    BigDecimal price = basketItems.stream()
            .filter(item -> !item.equals("Melon"))
            .mapToDouble(repository::findPrice)
            .mapToObj(BigDecimal::new)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return price.setScale(2, RoundingMode.HALF_EVEN).add(BigDecimal.valueOf(subTotalForMelons));
  }

  private int getNumberOfPairsOfMelons(List<String> items) {
    int numberOfMelons = numberOfMelons(items);
    if (numberOfMelons % 2 == 0) {
      return numberOfMelons / 2;
    } else {
      return numberOfMelons;
    }

  }

  private int numberOfMelons(List<String> basketItems) {
    return basketItems.stream()
            .collect(partitioningBy(item -> item.equals("Melon")))
            .get(true)
            .size();
  }
}
