package com.hanfak;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class HanBasketTest {

  private final HanBasket basket = new HanBasket();

  @Test
  void totalIs0WhenBasketIsEmpty() {
    assertThat(basket.total(emptyList())).isEqualTo(0.0);
  }

  @ParameterizedTest
  @CsvSource({
          "Apple, 0.35",
          "Banana, 0.20",
          "Melon, 0.50"
  })
  void totalOfBasketWithOneItem(String item, double total) {
    assertThat(basket.total(singletonList(item))).isEqualTo(total);
  }

  @Test
  void totalOfBasketWithMultipleItems() {
    assertThat(basket.total(Arrays.asList("Apple", "Banana"))).isEqualTo(0.55);
  }
}