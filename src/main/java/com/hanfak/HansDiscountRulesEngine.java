package com.hanfak;

import java.math.BigDecimal;
import java.util.List;

public class HansDiscountRulesEngine implements DiscountRulesEngine {

  private final DiscountedItemsRepository discountedItemsRepository;
  private final ItemPricesRepository itemPricesRepository;

  public HansDiscountRulesEngine(DiscountedItemsRepository discountedItemsRepository, ItemPricesRepository itemPricesRepository) {
    this.discountedItemsRepository = discountedItemsRepository;
    this.itemPricesRepository = itemPricesRepository;
  }

  @Override
  public BigDecimal calculatePriceOfDiscountedItems(final List<String> basketItems) {
   return discountedItemsRepository.findAll().stream()
           .map(item -> calculateDiscountedTotalForItem(basketItems, item))
           .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateDiscountedTotalForItem(final List<String> basketItems, final String item) {
    long numberOfDiscountedItem = basketItems.stream().filter(item::equals).count();
    DiscountType discountForItem = discountedItemsRepository.findDiscountForItem(item);
    return discountForItem.calculateTotal(numberOfDiscountedItem, itemPricesRepository.findPrice(item));
  }
}
