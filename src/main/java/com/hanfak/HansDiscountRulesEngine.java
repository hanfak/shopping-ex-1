package com.hanfak;

import java.math.BigDecimal;
import java.util.List;

public class HansDiscountRulesEngine implements DiscountRulesEngine {

  private final DiscountedItemsRepository discountedItemsRepository;

  public HansDiscountRulesEngine(DiscountedItemsRepository discountedItemsRepository) {
    this.discountedItemsRepository = discountedItemsRepository;
  }

  @Override
  public BigDecimal calculatePriceOfDiscountedItems(List<String> basketItems, ItemPricesRepository repository) {
    // TODO no calls to individual items
    return subtotalForLimes(basketItems, repository).add(
            subTotalForMelons(basketItems, repository)
    );
  }

  private BigDecimal subtotalForLimes(List<String> basketItems, ItemPricesRepository repository) {
    DiscountType limeDiscountType = discountedItemsRepository.findDiscountForItem("Lime");
    return limeDiscountType.calculateTotal(repository.findPrice("Lime"), basketItems);
  }

  private BigDecimal subTotalForMelons(List<String> basketItems, ItemPricesRepository repository) {
    DiscountType melonDiscountType = discountedItemsRepository.findDiscountForItem("Melon");
    return melonDiscountType.calculateTotal(repository.findPrice("Melon"), basketItems);
  }
}
