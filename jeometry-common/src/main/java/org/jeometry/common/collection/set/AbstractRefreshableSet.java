package org.jeometry.common.collection.set;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractRefreshableSet<V> extends AbstractDelegatingSet<V>
  implements RefreshableSet<V> {

  private Set<V> set = Collections.emptySet();

  private boolean valueLoaded;

  public AbstractRefreshableSet(final boolean editable) {
    super(editable);
  }

  @Override
  public synchronized void clearValue() {
    this.valueLoaded = false;
    this.set = Collections.emptySet();
  }

  @Override
  protected Set<V> getSet() {
    Set<V> set = this.set;
    if (!this.valueLoaded) {
      synchronized (this) {
        refreshIfNeeded();
        set = this.set;
      }
    }
    return set;
  }

  protected abstract Set<V> loadValue();

  @Override
  public synchronized void refresh() {
    final Set<V> set = loadValue();
    if (set == null) {
      this.set = Collections.emptySet();
    } else {
      this.set = set;
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
