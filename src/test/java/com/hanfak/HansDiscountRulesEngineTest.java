package com.hanfak;

import acceptancetests.DiscountedItemsRepositoryStub;
import acceptancetests.ItemPricesRepositoryStub;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class HansDiscountRulesEngineTest {

  @Test
  void subtotalOfBasketWithMultipleMelons() {
    List<String> basketItems = asList("Melon", "Melon", "Melon", "Melon", "Melon");
    BigDecimal subTotal = discountRulesEngine.calculatePriceOfDiscountedItems(basketItems);
    assertThat(subTotal.doubleValue()).isEqualTo(1.5);
  }

  @Test
  void subtotalOfBasketWithMultipleLimes() {
    List<String> basketItems = asList("Lime", "Lime", "Lime", "Lime", "Lime");
    BigDecimal subTotal = discountRulesEngine.calculatePriceOfDiscountedItems(basketItems);
    assertThat(subTotal.doubleValue()).isEqualTo(0.60);
  }

  @Test
  void subtotalOfBasketWithMultipleLimes2() {
    List<String> basketItems = asList("Lime", "Lime", "Lime",
                                      "Lime", "Lime", "Lime",
                                      "Lime", "Lime", "Lime");
    BigDecimal subTotal = discountRulesEngine.calculatePriceOfDiscountedItems(basketItems);
    assertThat(subTotal.doubleValue()).isEqualTo(0.90);
  }

  private final ItemPricesRepository itemRepository = new ItemPricesRepositoryStub();
  private final DiscountedItemsRepository discountedItemsRepository = new DiscountedItemsRepositoryStub();
  private final DiscountRulesEngine discountRulesEngine = new HansDiscountRulesEngine(discountedItemsRepository, itemRepository);
}