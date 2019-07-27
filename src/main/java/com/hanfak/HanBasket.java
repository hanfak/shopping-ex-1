package com.hanfak;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.RoundingMode.HALF_EVEN;

public class HanBasket {

  private final ItemPricesRepository itemPricesRepository;
  private final DiscountedItemsRepository discountedItemsRepository;
  private final DiscountRulesEngine discountRulesEngine;

  public HanBasket(ItemPricesRepository itemPricesRepository, DiscountedItemsRepository discountedItemsRepository, DiscountRulesEngine discountRulesEngine) {
    this.itemPricesRepository = itemPricesRepository;
    this.discountedItemsRepository = discountedItemsRepository;
    this.discountRulesEngine = discountRulesEngine;
  }

  public BigDecimal total(final List<String> basketItems) {
    final List<String> discountedItems = discountedItemsRepository.findAllItems();
    final List<String> discountedItemsInBasket = discountedItemsInBasket(basketItems, discountedItems);
    final BigDecimal discountedItemsTotal = discountRulesEngine.calculatePriceOfDiscountedItems(discountedItemsInBasket);
    final BigDecimal normalItemsTotal = priceOfAllNonDiscountedItems(basketItems, discountedItems);
    return calculateTotalOfBasket(normalItemsTotal, discountedItemsTotal);
  }

  private List<String> discountedItemsInBasket(final List<String> basketItems, final List<String> discountedItems) {
    return basketItems.stream()
            .filter(discountedItems::contains)
            .collect(Collectors.toList());
  }

  private BigDecimal priceOfAllNonDiscountedItems(final List<String> basketItems, final List<String> discountedItems) {
    return basketItems.stream()
            .filter(item -> !discountedItems.contains(item))
            .mapToDouble(itemPricesRepository::findPrice)
            .mapToObj(BigDecimal::new)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateTotalOfBasket(final BigDecimal normalItemsTotal, final BigDecimal discountedItemsTotal) {
    return normalItemsTotal
            .setScale(2, HALF_EVEN)
            .add(discountedItemsTotal);
  }
}
