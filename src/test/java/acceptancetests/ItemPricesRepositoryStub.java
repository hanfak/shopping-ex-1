package acceptancetests;

import com.hanfak.ItemPricesRepository;

import java.util.HashMap;
import java.util.Map;

public class ItemPricesRepositoryStub implements ItemPricesRepository {

  private static final Map<String, Double> ITEM_PRICES = new HashMap<>();
  static {
    ITEM_PRICES.put("Apple", 0.35);
    ITEM_PRICES.put("Banana", 0.20);
    ITEM_PRICES.put("Melon", 0.50);
    ITEM_PRICES.put("Lime", 0.15);
  }

  @Override
  public double findPrice(String item) {
    return ITEM_PRICES.get(item);
  }
}
