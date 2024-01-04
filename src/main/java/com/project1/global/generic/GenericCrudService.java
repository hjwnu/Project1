package com.project1.global.generic;


import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.global.utils.CustomBeanUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface GenericCrudService<E, P, R, U, ID> {
    E create(P postDto);
    E save(E entity);
    E findEntity(ID id);
    List<E> findList(int page, int size);
    E update(ID id, U patchDto);
    R getResponse(ID id);
    void delete(ID id);
    Page<R> findByName(String str, int page, int size);

    @Transactional
    abstract class GenericCrud<E, P, R, U, ID> implements GenericCrudService<E, P, R, U, ID> {
        protected abstract JpaRepository<E, ID> getRepository();
        protected abstract void setId(E newEntity, ID id);
        protected abstract GenericMapper<E, P, R, U, ID> getMapper();
        protected abstract List<E> findByName(String str);
        @Override
        public E create(P postDto) {
            E entity = getMapper().postDtoToEntity(postDto);
            return getRepository().save(entity);
        }
        public R entityToResponse(E entity){
            return getMapper().entityToResponseDto(entity);
        }
        @Override
        public E save(E entity) {
            return getRepository().save(entity);
        }
        @Override
        public E findEntity(ID id) {
            return verifyExist(id);
        }
        @Override
        public R getResponse(ID id) {
            return getMapper().entityToResponseDto(verifyExist(id));
        }
        @Override
        public List<E> findList(int page, int size) {

            List<E> findAll = getRepository().findAll();

            VerifiedNoEntity(findAll);
            return findAll;
        }
        @Override
        public E update(ID id, U patchDto) {
            E originEntity = verifyExist(id);
            E newEntity = getMapper().patchDtoToEntity(patchDto);
            setId(newEntity, id);
            CustomBeanUtils<E> customBeanUtils = new CustomBeanUtils<E>();
            customBeanUtils.copyNonNullProperties(newEntity, originEntity);
            return getRepository().save(originEntity);
        }
        @Override
        public void delete(ID id) {
            E e = verifyExist(id);
            getRepository().delete(e);
        }
        @Override
        public Page<R> findByName(String str, int page, int size) {
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            List<E> myContents = findByName(str);
            List<R> myContentsDto = getResponseList(myContents);
            return new PageImpl<>(myContentsDto, pageRequest, myContentsDto.size());
        }



        private E verifyExist(ID id) {
            Optional<E> optional
                    = getRepository().findById(id);
            return optional.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));
        }
        @NotNull
        private List<R> getResponseList(List<E> findAll) {
            List<R> findAllDto = new ArrayList<>();
            for (E e : findAll) {
                findAllDto.add(getMapper().entityToResponseDto(e));
            }
            return findAllDto;
        }
        private void VerifiedNoEntity(Page<R> findAll){
            if(findAll.getTotalElements()==0){
                throw new BusinessLogicException(ExceptionCode.NOT_FOUND);
            }
        }private void VerifiedNoEntity(List<E> findAll){
            if(findAll.size()==0){
                throw new BusinessLogicException(ExceptionCode.NOT_FOUND);
            }
        }
    }
}