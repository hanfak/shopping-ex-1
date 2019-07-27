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
  public BigDecimal calculatePriceOfDiscountedItems(List<String> basketItems) {
    // create map of item to number, go through each item, find subtotal for that item, add all prices
    // TODO no calls to individual items
    return subtotalForLimes(basketItems).add(
            subTotalForMelons(basketItems)
    );
  }

  private BigDecimal subtotalForLimes(List<String> basketItems) {
    DiscountType limeDiscountType = discountedItemsRepository.findDiscountForItem("Lime");
    return limeDiscountType.calculateTotal(itemPricesRepository.findPrice("Lime"), basketItems);
  }

  private BigDecimal subTotalForMelons(List<String> basketItems) {
    DiscountType melonDiscountType = discountedItemsRepository.findDiscountForItem("Melon");
    return melonDiscountType.calculateTotal(itemPricesRepository.findPrice("Melon"), basketItems);
  }
}
