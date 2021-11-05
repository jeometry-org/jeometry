package org.jeometry.common.data.refresh;

public abstract class AbstractRefreshableValue<V> implements RefreshableValue<V> {

  private V value;

  private boolean valueLoaded;

  @Override
  public V getValue() {
    V value = this.value;
    if (!this.valueLoaded) {
      synchronized (this) {
        refresh();
        value = this.value;
      }
    }
    return value;
  }

  protected abstract V loadValue();

  @Override
  public synchronized void refresh() {
    this.value = loadValue();
    this.valueLoaded = true;
  }

  @Override
  public synchronized void refreshIfNeeded() {
    if (!this.valueLoaded) {
      refresh();
    }
  }
}
