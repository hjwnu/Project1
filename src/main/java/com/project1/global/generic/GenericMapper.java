package com.project1.global.generic;

public interface GenericMapper<E, P, R, U, ID> {
    E postDtoToEntity(P postDto);
    E patchDtoToEntity(U patchDto);
    R entityToResponseDto(E entity);

}