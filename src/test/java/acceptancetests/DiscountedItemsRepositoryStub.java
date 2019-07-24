package acceptancetests;

import com.hanfak.DiscountType;
import com.hanfak.DiscountedItemsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountedItemsRepositoryStub implements DiscountedItemsRepository {
  private static final Map<String, DiscountType> ITEM_PRICES = new HashMap<>();

  static {
    ITEM_PRICES.put("Melon", DiscountType.DISCOUNT_1);
    ITEM_PRICES.put("Lime", DiscountType.DISCOUNT_2);
  }

  @Override
  public List<String> findAll() {
    return new ArrayList<>(ITEM_PRICES.keySet());
  }

  @Override
  public DiscountType findDiscountForItem(String item) {
    return ITEM_PRICES.get(item);
  }
}
