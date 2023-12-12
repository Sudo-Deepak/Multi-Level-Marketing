package com.deepak.mlm.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * This is the generic mapper class, any other interface can extends this.
 * @author Sudo-Deepak
 * @param <E>
 * @param <D>
 */
public interface EntityMapper<E, D> {
    E toEntity(D d);

    D toDTO(E e);

    List<E> toEntityList(List<D> dtoList);

    List<D> toDTOList(List<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D d);
}
