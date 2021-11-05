package org.jeometry.common.collection.list;

import java.util.List;
import java.util.function.Supplier;

class SupplierRefreshableList<V> extends AbstractRefreshableList<V> {

  private final Supplier<List<V>> supplier;

  public SupplierRefreshableList(final Supplier<List<V>> supplier, final boolean editable) {
    super(editable);
    this.supplier = supplier;
  }

  @Override
  protected List<V> loadValue() {
    return this.supplier.get();
  }

}
