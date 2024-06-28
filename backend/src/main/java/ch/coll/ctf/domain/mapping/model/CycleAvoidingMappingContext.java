/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package ch.coll.ctf.domain.mapping.model;

import java.util.IdentityHashMap;
import java.util.Map;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * A type to be used as {@link Context} parameter to track cycles in graphs.
 * <p>
 * Depending on the actual use case, the two methods below could also be changed
 * to only accept certain argument types,
 * e.g. base classes of graph nodes, avoiding the need to capture any other
 * objects that wouldn't necessarily result in
 * cycles.
 *
 * @author Andreas Gudian
 */
public class CycleAvoidingMappingContext {
  private Map<Object, Object> knownInstances = new IdentityHashMap<Object, Object>();

  @BeforeMapping
  public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
    Object knownInstance = knownInstances.get(source);

    if (targetType.isInstance(knownInstance)) return targetType.cast(knownInstance);
    return null;
  }

  @BeforeMapping
  public void storeMappedInstance(Object source, @MappingTarget Object target) {
    knownInstances.put(source, target);
  }
}
