package com.project1.global.generic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Getter  @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public abstract class GenericImage {
    @Column(length = 100)
    private String imageName;
    @Column
    private String originalName;
    @Column(length = 100)
    private String path;
    @Column
    private String representationImage; // "Yes" , " No" 문자열로 구분
    @Transient
    final String baseUrl = "http://be28.s3-website.ap-northeast-2.amazonaws.com";

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "Last_modified_at")
    private LocalDateTime modifiedAt;
}
