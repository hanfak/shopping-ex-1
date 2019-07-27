package com.hanfak;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

  public BigDecimal total(final List<String> basketItems) {
    final List<String> discountedItems = discountedItemsRepository.findAll();
    final List<String> discountedItemsInBasket = discountedItemsInBasket(basketItems, discountedItems);
    final BigDecimal discountedItemsTotal = discountRulesEngine.calculatePriceOfDiscountedItems(discountedItemsInBasket);

    return calculateTotalOfBasket(basketItems, discountedItems, discountedItemsTotal);
  }

  private List<String> discountedItemsInBasket(final List<String> basketItems, final List<String> discountedItems) {
    return basketItems.stream()
            .filter(discountedItems::contains)
            .collect(Collectors.toList());
  }

  private BigDecimal priceOfAllNonDiscountedItems(final List<String> basketItems, final List<String> discountedItems) {
    return basketItems.stream()
            .filter(item -> !discountedItems.contains(item))
            .mapToDouble(itemRepository::findPrice)
            .mapToObj(BigDecimal::new)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateTotalOfBasket(final List<String> basketItems, final List<String> discountedItems, final BigDecimal discountedItemsTotal) {
    return priceOfAllNonDiscountedItems(basketItems, discountedItems)
            .setScale(2, HALF_EVEN)
            .add(discountedItemsTotal);
  }
}
