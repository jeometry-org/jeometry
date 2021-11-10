package org.jeometry.common.collection.map;

import java.util.Collections;
import java.util.Map;

public abstract class AbstractRefreshableMap<K, V> extends AbstractDelegatingMap<K, V>
  implements RefreshableMap<K, V> {

  private Map<K, V> map = Collections.emptyMap();

  private boolean valueLoaded;

  public AbstractRefreshableMap(final boolean editable) {
    super(editable);
  }

  @Override
  public synchronized void clearValue() {
    this.valueLoaded = false;
    this.map = Collections.emptyMap();
  }

  @Override
  protected Map<K, V> getMap() {
    Map<K, V> map = this.map;
    if (!this.valueLoaded) {
      synchronized (this) {
        refreshIfNeeded();
        map = this.map;
      }
    }
    return map;
  }

  protected abstract Map<K, V> loadValue();

  @Override
  public synchronized void refresh() {
    final Map<K, V> map = loadValue();
    if (map == null) {
      this.map = Collections.emptyMap();
    } else {
      this.map = map;
    }
    this.valueLoaded = true;
  }

  @Override
  public synchronized void refreshIfNeeded() {
    if (!this.valueLoaded) {
      refresh();
    }
  }
}
