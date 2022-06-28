package org.jeometry.common.data.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jeometry.common.exception.Exceptions;

public class CollectionDataType extends SimpleDataType {

  private final DataType contentType;

  public CollectionDataType(final String name, final Class<?> javaClass,
    final DataType contentType) {
    super(name, javaClass);
    this.contentType = contentType;
  }

  @SuppressWarnings({
    "rawtypes", "unchecked"
  })
  private Object convertCollection(final Collection<?> collection) {
    try {
      final Class<?> javaClass = getJavaClass();
      final Collection<Object> newCollection;
      if (Collection.class == javaClass) {
        newCollection = new ArrayList<>();
      } else if (List.class == javaClass) {
        newCollection = new ArrayList<>();
      } else if (Set.class == javaClass) {
        newCollection = new LinkedHashSet<>();
      } else {
        final Constructor<?> declaredConstructor = javaClass.getDeclaredConstructor();
        newCollection = (Collection)declaredConstructor.newInstance();
      }
      newCollection.addAll(collection);
      return newCollection;
    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException
        | IllegalAccessException e) {
      throw Exceptions.wrap(e);
    }
  }

  public DataType getContentType() {
    return this.contentType;
  }

  @Override
  protected Object toObjectDo(final Object value) {
    if (value == null) {
      return null;
    } else if (value instanceof Collection) {
      final Collection<?> collection = (Collection<?>)value;
      return convertCollection(collection);

    } else {
      final DataType jsonListType = DataTypes.getDataType("JsonList");
      if (jsonListType == null) {
       return super.toObjectDo(value); 
      } else {
      final List<?> list = jsonListType.toObject(value);
      return convertCollection(list);
      }
    }
  }
}
