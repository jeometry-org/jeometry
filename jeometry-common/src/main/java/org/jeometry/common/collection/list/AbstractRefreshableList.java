package org.jeometry.common.collection.list;

import java.util.Collections;
import java.util.List;

public abstract class AbstractRefreshableList<V> extends AbstractDelegatingList<V>
  implements RefreshableList<V> {

  private List<V> list = Collections.emptyList();

  private boolean valueLoaded;

  public AbstractRefreshableList(final boolean editable) {
    super(editable);
  }

  @Override
  public synchronized void clearValue() {
    this.valueLoaded = false;
    this.list = Collections.emptyList();
  }

  @Override
  protected List<V> getList() {
    List<V> list = this.list;
    if (!this.valueLoaded) {
      synchronized (this) {
        refreshIfNeeded();
        list = this.list;
      }
    }
    return list;
  }

  protected abstract List<V> loadValue();

  @Override
  public synchronized void refresh() {
    final List<V> list = loadValue();
    if (list == null) {
      this.list = Collections.emptyList();
    } else {
      this.list = list;
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
