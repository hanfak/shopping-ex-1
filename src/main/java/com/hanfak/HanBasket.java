package com.hanfak;

import java.math.BigDecimal;
import java.util.List;

import static java.math.RoundingMode.HALF_EVEN;

public class HanBasket {

  private final ItemPricesRepository itemRepository;
  private final DiscountedItemsRepository discountedItemsRepository;
  private final DiscountRulesEngine discountRulesEngine;

  public HanBasket(ItemPricesRepository itemRepository, DiscountedItemsRepository discountedItemsRepository, DiscountRulesEngine discountRulesEngine) {
    this.itemRepository = itemRepository;
    this.discountedItemsRepository = discountedItemsRepository;
    this.discountRulesEngine = discountRulesEngine;
  }

  public BigDecimal total(List<String> basketItems) {
    List<String> discountedItems = discountedItemsRepository.findAll();
    BigDecimal nonDiscountedItemsTotal = priceOfAllNonDiscountedItems(basketItems, discountedItems);
    BigDecimal discountedItemsTotal = discountRulesEngine.calculatePriceOfDiscountedItems(basketItems, itemRepository);

    return nonDiscountedItemsTotal.setScale(2, HALF_EVEN)
            .add(discountedItemsTotal);
  }

  private BigDecimal priceOfAllNonDiscountedItems(List<String> basketItems, List<String> discountedItems) {
    return basketItems.stream()
            .filter(item -> !discountedItems.contains(item))
            .mapToDouble(itemRepository::findPrice)
            .mapToObj(BigDecimal::new)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
