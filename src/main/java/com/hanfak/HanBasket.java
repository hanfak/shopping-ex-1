package com.hanfak;

import java.math.BigDecimal;
import java.util.List;

import static java.math.RoundingMode.HALF_EVEN;
import static java.util.stream.Collectors.partitioningBy;

public class HanBasket {

  private final ItemPricesRepository repository;
  private final DiscountedItemsRepository discountedItemsRepository;

  public HanBasket(ItemPricesRepository repository, DiscountedItemsRepository discountedItemsRepository) {
    this.repository = repository;
    this.discountedItemsRepository = discountedItemsRepository;
  }

  public BigDecimal total(List<String> basketItems) {
    List<String> discountedItems = discountedItemsRepository.findAll();
    BigDecimal price = priceOfAllNonDiscountedItems(basketItems, discountedItems);

    return price.setScale(2, HALF_EVEN)
            .add(subTotalForMelons(basketItems))
            .add(subtotalForLimes(basketItems));
  }

  private BigDecimal subtotalForLimes(List<String> basketItems) {
    return BigDecimal.valueOf(numberOfLimesToPayFor(basketItems))
            .multiply(BigDecimal.valueOf(repository.findPrice("Lime")));
  }

  private BigDecimal subTotalForMelons(List<String> basketItems) {
    return BigDecimal.valueOf(numberOfMelonsToPayFor(basketItems))
            .multiply(BigDecimal.valueOf(repository.findPrice("Melon")));
  }

  private BigDecimal priceOfAllNonDiscountedItems(List<String> basketItems, List<String> discountedItems) {
    return basketItems.stream()
            .filter(item -> !discountedItems.contains(item))
            .mapToDouble(repository::findPrice)
            .mapToObj(BigDecimal::new)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private double numberOfLimesToPayFor(List<String> basketItems) {
    long numberOfLimes = basketItems.stream().filter("Lime"::equals).count();
    double numberOf3LotsOfLimes = Math.floor(numberOfLimes / 3.0);
    double leftOver = numberOfLimes % 3;
    return numberOf3LotsOfLimes * 2 + leftOver;
  }

  // TODO: Extract out a rules engine, to calculate price for discounted offers
  private int numberOfMelonsToPayFor(List<String> items) {
    return (int) Math.ceil(numberOfMelons(items) / 2.0);
  }

  @SuppressWarnings("Convert2MethodRef") // Easier to read
  private int numberOfMelons(List<String> basketItems) {
    return basketItems.stream()
            .collect(partitioningBy(item -> "Melon".equals(item)))
            .get(true)
            .size();
  }
}
