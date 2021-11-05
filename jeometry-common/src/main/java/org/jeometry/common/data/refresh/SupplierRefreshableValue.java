package org.jeometry.common.data.refresh;

import java.util.function.Supplier;

class SupplierRefreshableValue<V> extends AbstractRefreshableValue<V> {

  private final Supplier<V> supplier;

  public SupplierRefreshableValue(final Supplier<V> supplier) {
    this.supplier = supplier;
  }

  @Override
  protected V loadValue() {
    return this.supplier.get();
  }

}
