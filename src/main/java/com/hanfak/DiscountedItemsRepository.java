package com.hanfak;

import java.util.List;

public interface DiscountedItemsRepository {
  List<String> findAllItems();

  DiscountType findDiscountTypeForItem(String item);
}
