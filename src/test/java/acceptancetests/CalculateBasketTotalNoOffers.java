package acceptancetests;

import com.hanfak.HanBasket;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CalculateBasketTotalNoOffers {

  private double actualTotal;
  private final HanBasket basket = new HanBasket();

  @Test
  void listOfUniqueItems() {
    whenProcessingTheFollowingItems("Apple", "Banana", "Melon");

    thenTheTotalOfTheBasketIs(1.05);
  }

  private void whenProcessingTheFollowingItems(String... fruits) {
    List<String> basketItems = new ArrayList<>(Arrays.asList(fruits));
    actualTotal= basket.total(basketItems);
  }

  private void thenTheTotalOfTheBasketIs(double total) {
    Assertions.assertThat(actualTotal).isEqualTo(total);
  }
}
