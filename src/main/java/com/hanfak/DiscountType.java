package com.hanfak;

import java.math.BigDecimal;

public enum DiscountType {
  //Buy one get one free
  DISCOUNT_1 {
    @Override
    public BigDecimal calculateTotal(long numberOfDiscountedItem, double value) {
      BigDecimal price = BigDecimal.valueOf(value);

      int numberOfDiscountedItemsToPayFor = (int) Math.ceil(numberOfDiscountedItem / 2.0);

      return BigDecimal.valueOf(numberOfDiscountedItemsToPayFor).multiply(price);
    }
  },

  //Buy three pay for two
  DISCOUNT_2 {
    @Override
    public BigDecimal calculateTotal(long numberOfDiscountedItem, double value) {
      BigDecimal price = BigDecimal.valueOf(value);
      double numberOf3LotsOfItems = Math.floor(numberOfDiscountedItem / 3.0);
      double leftOver = numberOfDiscountedItem % 3;
      double numberOfDiscountedItemsToPayFor = numberOf3LotsOfItems * 2 + leftOver;

      return BigDecimal.valueOf(numberOfDiscountedItemsToPayFor).multiply(price);
    }
  };

  public abstract BigDecimal calculateTotal(long numberOfDiscountedItem, double price);
}
