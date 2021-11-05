package org.jeometry.common.collection.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class AbstractDelegatingList<V> implements List<V> {
  public static <V1> ListIterator<V1> unmodifiableListIterator(final ListIterator<V1> iterator) {
    return new ListIterator<V1>() {
      @Override
      public void add(final V1 e) {
        throw new IllegalStateException("List is not editable");
      }

      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public boolean hasPrevious() {
        return iterator.hasPrevious();
      }

      @Override
      public V1 next() {
        return iterator.next();
      }

      @Override
      public int nextIndex() {
        return iterator.nextIndex();
      }

      @Override
      public V1 previous() {
        return iterator.previous();
      }

      @Override
      public int previousIndex() {
        return iterator.previousIndex();
      }

      @Override
      public void remove() {
        throw new IllegalStateException("List is not editable");
      }

      @Override
      public void set(final V1 e) {
        throw new IllegalStateException("List is not editable");
      }

      @Override
      public String toString() {
        return iterator.toString();
      }
    };
  }

  private boolean editable = true;

  public AbstractDelegatingList(final boolean editable) {
    this.editable = editable;
  }

  @Override
  public void add(final int index, final V element) {
    final List<V> list = getEditableList();
    list.add(index, element);
  }

  @Override
  public boolean add(final V e) {
    final List<V> list = getEditableList();
    return list.add(e);
  }

  @Override
  public boolean addAll(final Collection<? extends V> c) {
    final List<V> list = getEditableList();
    return list.addAll(c);
  }

  @Override
  public boolean addAll(final int index, final Collection<? extends V> c) {
    final List<V> list = getEditableList();
    return list.addAll(c);
  }

  @Override
  public void clear() {
    final List<V> list = getEditableList();
    list.clear();
  }

  @Override
  public boolean contains(final Object o) {
    return this.getList().contains(o);
  }

  @Override
  public boolean containsAll(final Collection<?> c) {
    final List<V> list = getList();
    return list.containsAll(c);
  }

  @Override
  public boolean equals(final Object obj) {
    final List<V> list = getList();
    return list.equals(obj);
  }

  @Override
  public void forEach(final Consumer<? super V> action) {
    final List<V> list = getList();
    list.forEach(action);
  }

  @Override
  public V get(final int index) {
    final List<V> list = getList();
    return list.get(index);
  }

  protected List<V> getEditableList() {
    if (!isEditable()) {
      throw new IllegalStateException("List is not editable");
    }
    return getList();
  }

  protected abstract List<V> getList();

  @Override
  public int hashCode() {
    final List<V> list = getList();
    return list.hashCode();
  }

  @Override
  public int indexOf(final Object o) {
    final List<V> list = getList();
    return list.indexOf(o);
  }

  public boolean isEditable() {
    return this.editable;
  }

  @Override
  public boolean isEmpty() {
    final List<V> list = getList();
    return list.isEmpty();
  }

  @Override
  public Iterator<V> iterator() {
    final List<V> list = getList();
    final Iterator<V> iterator = list.iterator();
    if (isEditable()) {
      return iterator;
    } else {
      return new Iterator<V>() {
        @Override
        public boolean hasNext() {
          return iterator.hasNext();
        }

        @Override
        public V next() {
          return iterator.next();
        }

        @Override
        public String toString() {
          return iterator.toString();
        }
      };
    }
  }

  @Override
  public int lastIndexOf(final Object o) {
    final List<V> list = getList();
    return list.lastIndexOf(o);
  }

  @Override
  public ListIterator<V> listIterator() {
    final List<V> list = getList();
    final ListIterator<V> iterator = list.listIterator();
    if (isEditable()) {
      return iterator;
    } else {
      return unmodifiableListIterator(iterator);
    }
  }

  @Override
  public ListIterator<V> listIterator(final int index) {
    final List<V> list = getList();
    final ListIterator<V> listIterator = list.listIterator(index);
    return unmodifiableListIterator(listIterator);
  }

  @Override
  public Stream<V> parallelStream() {
    return this.getList().parallelStream();
  }

  @Override
  public V remove(final int index) {
    final List<V> list = getEditableList();
    return list.remove(index);
  }

  @Override
  public boolean remove(final Object o) {
    final List<V> list = getEditableList();
    return list.remove(o);
  }

  @Override
  public boolean removeAll(final Collection<?> c) {
    final List<V> list = getEditableList();
    return list.removeAll(c);
  }

  @Override
  public boolean removeIf(final Predicate<? super V> filter) {
    final List<V> list = getEditableList();
    return list.removeIf(filter);
  }

  @Override
  public boolean retainAll(final Collection<?> c) {
    final List<V> list = getEditableList();
    return list.retainAll(c);
  }

  @Override
  public V set(final int index, final V element) {
    final List<V> list = getEditableList();
    return list.set(index, element);
  }

  @Override
  public int size() {
    final List<V> list = getList();
    return list.size();
  }

  @Override
  public void sort(final Comparator<? super V> c) {
    final List<V> list = getEditableList();
    list.sort(c);
  }

  @Override
  public Spliterator<V> spliterator() {
    return getList().spliterator();
  }

  @Override
  public Stream<V> stream() {
    return this.getList().stream();
  }

  @Override
  public List<V> subList(final int fromIndex, final int toIndex) {
    final List<V> list = getList();
    return list.subList(fromIndex, toIndex);
  }

  @Override
  public Object[] toArray() {
    final List<V> list = getList();
    return list.toArray();
  }

  @Override
  public <T> T[] toArray(final IntFunction<T[]> generator) {
    final List<V> list = getList();
    return list.toArray(generator);
  }

  @Override
  public <T> T[] toArray(final T[] a) {
    final List<V> list = getList();
    return list.toArray(a);
  }

  @Override
  public String toString() {
    final List<V> list = getList();
    return list.toString();
  }

}
