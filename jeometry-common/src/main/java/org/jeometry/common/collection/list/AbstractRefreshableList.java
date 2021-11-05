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
  protected List<V> getList() {
    List<V> map = this.list;
    if (!this.valueLoaded) {
      synchronized (this) {
        refresh();
        map = this.list;
      }
    }
    return map;
  }

  protected abstract List<V> loadValue();

  @Override
  public synchronized void refresh() {
    final List<V> map = loadValue();
    if (map == null) {
      this.list = Collections.emptyList();
    } else {
      this.list = map;
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
