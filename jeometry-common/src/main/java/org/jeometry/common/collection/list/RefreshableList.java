package org.jeometry.common.collection.list;

import java.util.List;
import java.util.function.Supplier;

import org.jeometry.common.data.refresh.Refreshable;

public interface RefreshableList<V> extends Refreshable, List<V> {
  static <V1> RefreshableList<V1> supplier(final Supplier<List<V1>> supplier) {
    return new SupplierRefreshableList<V1>(supplier, true);
  }

  static <V1> RefreshableList<V1> supplier(final Supplier<List<V1>> supplier,
    final boolean editable) {
    return new SupplierRefreshableList<V1>(supplier, editable);
  }
}
