package com.hanfak;


import acceptancetests.DiscountedItemsRepositoryStub;
import acceptancetests.ItemPricesRepositoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HanBasketTest {

  @Test
  void totalIs0WhenBasketIsEmpty() {
    assertThat(basket.total(emptyList()).doubleValue()).isEqualTo(0.0);
  }

  @ParameterizedTest
  @CsvSource({
          "Apple, 0.35",
          "Banana, 0.20"
  })
  void totalOfBasketWithOneItem(String item, double value) {
    assertThat(basket.total(singletonList(item)).doubleValue()).isEqualTo(value);
  }

  @Test
  void totalOfBasketWithOneLime() {
    when(discountRulesEngine.calculatePriceOfDiscountedItems(singletonList("Lime")))
            .thenReturn(BigDecimal.valueOf(0.15));

    assertThat(basket.total(singletonList("Lime")).doubleValue()).isEqualTo(0.15);
  }

  @Test
  void totalOfBasketWithOneMelon() {
    when(discountRulesEngine.calculatePriceOfDiscountedItems(singletonList("Melon")))
            .thenReturn(BigDecimal.valueOf(0.50));

    assertThat(basket.total(singletonList("Melon")).doubleValue()).isEqualTo(0.50);
  }

  @Test
  void totalOfBasketWithMultipleNonDiscountedItems() {
    assertThat(basket.total(asList("Apple", "Banana")).doubleValue()).isEqualTo(0.55);
  }

  @Test
  void totalOfBasketWithMultipleAndDuplicateNonDiscountedItems() {
    assertThat(basket.total(asList("Apple", "Apple", "Banana")).doubleValue())
            .isEqualTo(0.90);
  }

  @BeforeEach
  void setUp() {
    when(discountRulesEngine.calculatePriceOfDiscountedItems(emptyList()))
            .thenReturn(BigDecimal.ZERO);
  }

  private final ItemPricesRepository repository = new ItemPricesRepositoryStub();
  private final DiscountedItemsRepository discountedItemsRepository = new DiscountedItemsRepositoryStub();
  private final DiscountRulesEngine discountRulesEngine = mock(DiscountRulesEngine.class);
  private final HanBasket basket = new HanBasket(repository, discountedItemsRepository, discountRulesEngine);
}