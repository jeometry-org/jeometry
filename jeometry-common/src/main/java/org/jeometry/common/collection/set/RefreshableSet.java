package org.jeometry.common.collection.set;

import java.util.Set;
import java.util.function.Supplier;

import org.jeometry.common.collection.collection.RefreshableCollection;

public interface RefreshableSet<V> extends RefreshableCollection<V>, Set<V> {
  static <V1> SupplierRefreshableSet<V1> supplier(final Supplier<Set<V1>> supplier) {
    return new SupplierRefreshableSet<V1>(supplier, true);
  }

  static <V1> SupplierRefreshableSet<V1> supplier(final Supplier<Set<V1>> supplier,
    final boolean editable) {
    return new SupplierRefreshableSet<V1>(supplier, editable);
  }

}
