package com.hanfak;


import acceptancetests.DiscountedItemsRepositoryStub;
import acceptancetests.ItemPricesRepositoryStub;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HanBasketTest {

  @Test
  void totalIs0WhenBasketIsEmpty() {
    when(discountRulesEngine.calculatePriceOfDiscountedItems(emptyList(), repository))
            .thenReturn(BigDecimal.ZERO);
    assertThat(basket.total(emptyList()).doubleValue()).isEqualTo(0.0);
  }

  // TODO remove??
  @ParameterizedTest
  @CsvSource({
          "Apple, 0.35",
          "Banana, 0.20"
  })
  void totalOfBasketWithOneItem(String item, double value) {
    when(discountRulesEngine.calculatePriceOfDiscountedItems(singletonList(item), repository))
            .thenReturn(BigDecimal.ZERO);

    assertThat(basket.total(singletonList(item)).doubleValue()).isEqualTo(value);
  }

  @Test
  void totalOfBasketWithOneLime() {
    when(discountRulesEngine.calculatePriceOfDiscountedItems(singletonList("Lime"), repository))
            .thenReturn(BigDecimal.valueOf(0.15));

    assertThat(basket.total(singletonList("Lime")).doubleValue()).isEqualTo(0.15);
  }

  @Test
  void totalOfBasketWithOneMelon() {
    when(discountRulesEngine.calculatePriceOfDiscountedItems(singletonList("Melon"), repository))
            .thenReturn(BigDecimal.valueOf(0.50));

    assertThat(basket.total(singletonList("Melon")).doubleValue()).isEqualTo(0.50);
  }

  @Test
  void totalOfBasketWithMultipleItems() {
    when(discountRulesEngine.calculatePriceOfDiscountedItems(Arrays.asList("Apple", "Banana"), repository))
            .thenReturn(BigDecimal.ZERO);
    assertThat(basket.total(asList("Apple", "Banana")).doubleValue()).isEqualTo(0.55);
  }

  @Test
  void totalOfBasketWithMultipleAndDuplicateItems() {
    when(discountRulesEngine.calculatePriceOfDiscountedItems(Arrays.asList("Apple", "Apple", "Banana"), repository))
            .thenReturn(BigDecimal.ZERO);
    assertThat(basket.total(asList("Apple", "Apple", "Banana")).doubleValue())
            .isEqualTo(0.90);
  }

  private final ItemPricesRepository repository = new ItemPricesRepositoryStub();
  private final DiscountedItemsRepository discountedItemsRepository = new DiscountedItemsRepositoryStub();
  private final DiscountRulesEngine discountRulesEngine = mock(DiscountRulesEngine.class);
  private final HanBasket basket = new HanBasket(repository, discountedItemsRepository, discountRulesEngine);
}