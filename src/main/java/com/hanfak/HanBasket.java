package com.hanfak;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HanBasket {

  private static final Map<String, Double> ITEM_PRICES = new HashMap<>();
  static {
    ITEM_PRICES.put("Apple", 0.35);
    ITEM_PRICES.put("Banana", 0.20);
    ITEM_PRICES.put("Melon", 0.50);
  }

  public Double total(List<String> basketItems) {
    return basketItems.stream()
            .mapToDouble(ITEM_PRICES::get)
            .sum();
  }
}
