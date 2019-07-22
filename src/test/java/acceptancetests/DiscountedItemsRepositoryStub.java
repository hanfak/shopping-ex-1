package acceptancetests;

import com.hanfak.DiscountedItemsRepository;

import java.util.ArrayList;
import java.util.List;

public class DiscountedItemsRepositoryStub implements DiscountedItemsRepository {
  private static final List<String> DISCOUNTED_ITEMS = new ArrayList<>();
  static {
    DISCOUNTED_ITEMS.add("Melon");
    DISCOUNTED_ITEMS.add("Lime");
  }
  @Override
  public List<String> findAll() {
    return DISCOUNTED_ITEMS;
  }
}
