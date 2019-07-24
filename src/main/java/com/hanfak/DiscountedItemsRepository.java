package com.hanfak;

import java.util.List;

public interface DiscountedItemsRepository {
  List<String> findAll();

  DiscountType findDiscountForItem(String item);
}
