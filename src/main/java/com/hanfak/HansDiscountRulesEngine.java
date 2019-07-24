package com.hanfak;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.partitioningBy;

public class HansDiscountRulesEngine implements DiscountRulesEngine {

  @Override
  public BigDecimal calculatePriceOfDiscountedItems(List<String> basketItems, ItemPricesRepository repository) {
    return subtotalForLimes(basketItems, repository).add(
            subTotalForMelons(basketItems, repository)
    );
  }

  private double numberOfLimesToPayFor(List<String> basketItems) {
    long numberOfLimes = basketItems.stream().filter("Lime"::equals).count();
    double numberOf3LotsOfLimes = Math.floor(numberOfLimes / 3.0);
    double leftOver = numberOfLimes % 3;
    return numberOf3LotsOfLimes * 2 + leftOver;
  }

  // TODO: Extract out a rules engine, to calculate price for discounted offers
  private int numberOfMelonsToPayFor(List<String> items) {
    return (int) Math.ceil(numberOfMelons(items) / 2.0);
  }

  @SuppressWarnings("Convert2MethodRef") // Easier to read
  private int numberOfMelons(List<String> basketItems) {
    return basketItems.stream()
            .collect(partitioningBy(item -> "Melon".equals(item)))
            .get(true)
            .size();
  }

  private BigDecimal subtotalForLimes(List<String> basketItems, ItemPricesRepository repository) {
    return BigDecimal.valueOf(numberOfLimesToPayFor(basketItems))
            .multiply(BigDecimal.valueOf(repository.findPrice("Lime")));
  }

  private BigDecimal subTotalForMelons(List<String> basketItems, ItemPricesRepository repository) {
    return BigDecimal.valueOf(numberOfMelonsToPayFor(basketItems))
            .multiply(BigDecimal.valueOf(repository.findPrice("Melon")));
  }
}
