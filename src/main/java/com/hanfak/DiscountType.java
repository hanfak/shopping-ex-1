package com.hanfak;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.partitioningBy;

public enum DiscountType {
  //Buy one get one free
  DISCOUNT_1 {
    @Override
    public BigDecimal calculateTotal(double value, List<String> basketItems) {
      BigDecimal price = BigDecimal.valueOf(value);
      // Extract call to specific item
      return BigDecimal.valueOf(numberOfMelonsToPayFor(basketItems)).multiply(price);
    }
  },

  //Buy three pay for two
  DISCOUNT_2 {
    @Override
    public BigDecimal calculateTotal(double value, List<String> basketItems) {
      BigDecimal price = BigDecimal.valueOf(value);
      // Extract call to specific item
      return BigDecimal.valueOf(numberOfLimesToPayFor(basketItems)).multiply(price);
    }
  };

  public abstract BigDecimal calculateTotal(double value, List<String> basketItems);

  private static int numberOfMelonsToPayFor(List<String> items) {
    return (int) Math.ceil(numberOfMelons(items) / 2.0);
  }

  @SuppressWarnings("Convert2MethodRef") // Easier to read
  private static int numberOfMelons(List<String> basketItems) {
    return basketItems.stream()
            .collect(partitioningBy(item -> "Melon".equals(item)))
            .get(true)
            .size();
  }

  private static double numberOfLimesToPayFor(List<String> basketItems) {
    long numberOfLimes = basketItems.stream().filter("Lime"::equals).count();
    double numberOf3LotsOfLimes = Math.floor(numberOfLimes / 3.0);
    double leftOver = numberOfLimes % 3;
    return numberOf3LotsOfLimes * 2 + leftOver;
  }
}
