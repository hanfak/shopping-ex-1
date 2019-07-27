package acceptancetests;

import com.hanfak.DiscountType;
import com.hanfak.DiscountedItemsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hanfak.DiscountType.DISCOUNT_1;
import static com.hanfak.DiscountType.DISCOUNT_2;

public class DiscountedItemsRepositoryStub implements DiscountedItemsRepository {
  private static final Map<String, DiscountType> ITEM_PRICES = new HashMap<>();

  static {
    ITEM_PRICES.put("Melon", DISCOUNT_1);
    ITEM_PRICES.put("Lime", DISCOUNT_2);
  }

  @Override
  public List<String> findAll() {
    return new ArrayList<>(ITEM_PRICES.keySet());
  }

  @Override
  public DiscountType findDiscountForItem(String item) {
    return ITEM_PRICES.get(item);
  }

  public static void main(String... args) {
    List<String> keys = ITEM_PRICES.entrySet().stream()
            .filter(entry -> entry.getValue().equals(DISCOUNT_1))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    System.out.println(keys);
  }
}
