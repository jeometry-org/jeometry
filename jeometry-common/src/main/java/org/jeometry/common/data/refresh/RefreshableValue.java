package org.jeometry.common.data.refresh;

import java.util.function.Supplier;

public interface RefreshableValue<V> extends Refreshable {
  static <T> RefreshableValue<T> supplier(final Supplier<T> supplier) {
    return new SupplierRefreshableValue<T>(supplier);
  }

  V getValue();

}
