package ch.coll.ctf.adapter.repository.jpa;

import static org.mapstruct.CollectionMappingStrategy.ADDER_PREFERRED;
import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = SPRING, collectionMappingStrategy = ADDER_PREFERRED, injectionStrategy = CONSTRUCTOR, nullValueIterableMappingStrategy = RETURN_NULL)
public interface EntityMapperConfig {
}
