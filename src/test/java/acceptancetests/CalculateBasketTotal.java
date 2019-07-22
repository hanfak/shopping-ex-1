package acceptancetests;

import com.hanfak.HanBasket;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CalculateBasketTotal {

  @Nested
  class NoDiscounts {

    @Test
    void listOfUniqueItems() {
      whenProcessingTheFollowingItems("Apple", "Banana", "Melon");

      thenTheTotalOfTheBasketIs(1.05);
    }
  }

  @Nested
  class Discounts {

    @Test
    void buyOneGetOneFreeForMelons() {
      whenProcessingTheFollowingItems("Melon", "Banana", "Melon");

      thenTheTotalOfTheBasketIs(0.70);
    }

    @Test
    void threeForPriceOfTwoForLimes() {
      whenProcessingTheFollowingItems("Lime", "Banana", "Lime", "Apple", "Lime");

      thenTheTotalOfTheBasketIs(0.85);
    }
  }


  private void whenProcessingTheFollowingItems(String... fruits) {
    List<String> basketItems = new ArrayList<>(Arrays.asList(fruits));
    actualTotal = basket.total(basketItems);
  }

  private void thenTheTotalOfTheBasketIs(double total) {
    Assertions.assertThat(actualTotal.doubleValue()).isEqualTo(total);
  }

  private BigDecimal actualTotal;
  private final ItemPricesRepositoryStub repository = new ItemPricesRepositoryStub();
  private final HanBasket basket = new HanBasket(repository);
}
