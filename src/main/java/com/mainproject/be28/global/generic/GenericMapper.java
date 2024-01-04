package com.mainproject.be28.global.generic;

public interface GenericMapper<E, P, R, U, ID> {
    E postDtoToEntity(P postDto);
    E patchDtoToEntity(U patchDto);
    R entityToResponseDto(E entity);

}