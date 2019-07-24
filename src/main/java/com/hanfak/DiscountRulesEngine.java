package com.hanfak;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountRulesEngine {
  BigDecimal calculatePriceOfDiscountedItems(List<String> basketItems, ItemPricesRepository repository);
}
