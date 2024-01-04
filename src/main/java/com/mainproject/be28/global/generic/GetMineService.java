package com.mainproject.be28.global.generic;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.member.service.Layer2.MemberVerifyService;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public interface GetMineService<E, R> {
    Page<R> getMine(int page, int size);

    abstract class GetMineServiceImpl<E,R> implements GetMineService<E, R> {
        protected final MemberVerifyService memberVerifyService;
        protected final EntityManager entityManager;
        protected final Class<E> entityClass;
        protected GetMineServiceImpl(MemberVerifyService memberVerifyService, EntityManager entityManager, Class<E> entityClass) {
            this.memberVerifyService = memberVerifyService;
            this.entityManager = entityManager;
            this.entityClass = entityClass;
        }
        protected abstract R entityToResponseDto(E e);
        protected abstract void setMemberAndItem(R r, E e) ;

        public Page<R> getMine(int page, int size) {
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            List<E> myContents = fetchJoinWithMember(getMemberName());
            List<R> myContentsDto = new ArrayList<>();
            for (E e : myContents) {
                R response = entityToResponseDto(e);
                setMemberAndItem(response, e);
                myContentsDto.add(response);
            }
            return new PageImpl<>(myContentsDto, pageRequest, myContentsDto.size());
        }

        protected TypedQuery<E> getQuery() {
            return entityManager.createQuery(
                    "SELECT e FROM " + entityClass.getSimpleName() + " e JOIN FETCH e. member m JOIN FETCH e.item i WHERE m.name = :name", entityClass);
        }

        private List<E> fetchJoinWithMember(String memberName) {
            TypedQuery<E> query = getQuery();
            query.setParameter("name", memberName);
            return query.getResultList();
        }


        private String getMemberName() {
        Member member = memberVerifyService.findTokenMember();
        if (member != null) {
            return member.getName();
        } else {
            throw new BusinessLogicException(ExceptionCode.NOT_FOUND);
        }
    }
    }
}