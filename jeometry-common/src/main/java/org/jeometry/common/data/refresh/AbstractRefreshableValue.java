package org.jeometry.common.data.refresh;

public abstract class AbstractRefreshableValue<V> implements RefreshableValue<V> {

  private V value;

  private boolean valueLoaded;

  @Override
  public synchronized void clearValue() {
    this.valueLoaded = false;
    this.value = null;
  }

  @Override
  public V getValue() {
    final V value = this.value;
    if (!this.valueLoaded) {
      return this.refreshValueIfNeeded();
    }
    return value;
  }

  protected abstract V loadValue();

  @Override
  public synchronized void refreshIfNeeded() {
    refreshValueIfNeeded();
  }

  @Override
  public synchronized V refreshValue() {
    this.value = loadValue();
    this.valueLoaded = true;
    return this.value;
  }

  @Override
  public synchronized V refreshValueIfNeeded() {
    if (!this.valueLoaded) {
      refresh();
    }
    return this.value;
  }
}
