package com.hanfak;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class HansDiscountRulesEngine implements DiscountRulesEngine {

  private final DiscountedItemsRepository discountedItemsRepository;
  private final ItemPricesRepository itemPricesRepository;

  public HansDiscountRulesEngine(DiscountedItemsRepository discountedItemsRepository, ItemPricesRepository itemPricesRepository) {
    this.discountedItemsRepository = discountedItemsRepository;
    this.itemPricesRepository = itemPricesRepository;
  }

  @Override
  public BigDecimal calculatePriceOfDiscountedItems(List<String> basketItems) {
    // create map of item to number, go through each item, find subtotal for that item, add all prices
   return discountedItemsRepository.findAll().stream()
           .map(item -> calculateDiscountedTotalForItem(basketItems, item))
           .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateDiscountedTotalForItem(List<String> basketItems, String item) {
    DiscountType discountForItem = discountedItemsRepository.findDiscountForItem(item);
    return discountForItem.calculateTotal(itemPricesRepository.findPrice(item), basketItems);
  }
}
