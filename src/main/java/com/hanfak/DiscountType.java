package com.hanfak;

import java.math.BigDecimal;
import java.util.List;

public enum DiscountType {
  //Buy one get one free
  DISCOUNT_1 {
    @Override
    public BigDecimal calculateTotal(final double value, final List<String> basketItems) {
      BigDecimal price = BigDecimal.valueOf(value);
      // Extract call to specific item
      return BigDecimal.valueOf(numberOfMelonsToPayFor(basketItems)).multiply(price);
    }
  },

  //Buy three pay for two
  DISCOUNT_2 {
    @Override
    public BigDecimal calculateTotal(final double value, final List<String> basketItems) {
      BigDecimal price = BigDecimal.valueOf(value);
      // Extract call to specific item
      return BigDecimal.valueOf(numberOfLimesToPayFor(basketItems)).multiply(price);
    }
  };

  public abstract BigDecimal calculateTotal(final double value, final List<String> basketItems);

  private static int numberOfMelonsToPayFor(final List<String> items) {
    return (int) Math.ceil(numberOfMelons(items) / 2.0);
  }

  @SuppressWarnings("Convert2MethodRef") // Easier to read
  private static long numberOfMelons(final List<String> basketItems) {
    return getNumberOfItems(basketItems, "Melon");
  }

  private static double numberOfLimesToPayFor(final List<String> basketItems) {
    long numberOfLimes = getNumberOfItems(basketItems, "Lime");
    double numberOf3LotsOfLimes = Math.floor(numberOfLimes / 3.0);
    double leftOver = numberOfLimes % 3;
    return numberOf3LotsOfLimes * 2 + leftOver;
  }

  private static long getNumberOfItems(final List<String> basketItems, final String item) {
    return basketItems.stream().filter(item::equals).count();
  }
}
