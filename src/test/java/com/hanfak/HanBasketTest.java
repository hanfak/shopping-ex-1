package com.hanfak;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

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
          "Banana, 0.20",
          "Melon, 0.50"
  })
  void totalOfBasketWithOneItem(String item, double value) {
    when(repository.findPrice(item)).thenReturn(value);
    assertThat(basket.total(singletonList(item)).doubleValue()).isEqualTo(value);
  }

  @Test
  void totalOfBasketWithMultipleItems() {
    when(repository.findPrice("Apple")).thenReturn(0.35);
    when(repository.findPrice("Banana")).thenReturn(0.20);
    assertThat(basket.total(Arrays.asList("Apple", "Banana")).doubleValue()).isEqualTo(0.55);
  }

  @Test
  void totalOfBasketWithMultipleAndDuplicateItems() {
    when(repository.findPrice("Apple")).thenReturn(0.35).thenReturn(0.35);
    when(repository.findPrice("Banana")).thenReturn(0.20);
    assertThat(basket.total(Arrays.asList("Apple", "Apple", "Banana")).doubleValue())
            .isEqualTo(0.90);
  }

  private final ItemPricesRepository repository =  mock(ItemPricesRepository.class);
  private final HanBasket basket = new HanBasket(repository);
}