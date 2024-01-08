package com.project1.global.generic;

import com.project1.domain.shopping.item.entity.Item;
import com.project1.global.utils.S3;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface GenericImageService<E, R> {
    @Transactional
    void updateImages(List<MultipartFile> imgFileList, Item findItem, Item updatedItem) throws IOException;
    List<E> saveImage(E e, List<MultipartFile> imgFileList) throws IOException;
    Map<Long, List<R>> fetchItemImages(List<Long> idList);
    void deleteImage(E e);

    @Slf4j
    @Transactional
    abstract class GenericImageServiceImpl<E, T extends GenericImage, R> {
        protected final S3 s3;

        public GenericImageServiceImpl(S3 s3) {
            this.s3 = s3;
        }
        protected abstract JpaRepository<T, Long> getRepository();
        protected abstract Map<Long, List<R>> fetchImages(List<Long> ids);
        protected abstract List<T> getImages(E e);
        protected abstract void setImages(E entity, List<T> images);
        protected abstract T imageBuild(E e, MultipartFile file, String name);
        @NotNull
        protected abstract String generateFileName(MultipartFile file, E e);
        protected abstract Map<Long, List<T>> fetch(List<Long> ids);
        public void update(List<MultipartFile> imgFileList, E findEntity, E updatedEntity) throws IOException {
            List<T> imageList = getImages(findEntity)==null? new ArrayList<>():getImages(findEntity);
            if (imgFileList != null) {
                for (MultipartFile image : imgFileList) {
                    T img = uploadS3(image, updatedEntity);
                    imageList.add(img);
                }
                setImages(updatedEntity,imageList);
                getRepository().saveAll(imageList);
            }
        }
        public void delete(E e) {
            List<T> images = getImages(e);
            for (T image : images) {
                s3.deleteFile(image.getImageName());
            }
        }
        public List<T> saveAll(List<MultipartFile> imgFileList, E entity) throws IOException {
            return (imgFileList == null) ? Collections.emptyList() : save(imgFileList,entity);
        }

        private List<T> save(List<MultipartFile> imgFileList,E entity) throws IOException {
            List<T> images = new ArrayList<>();
            boolean isFirstImage = true;

            for (MultipartFile file : imgFileList) {
                T image = uploadS3(file, entity);
                image.setRepresentationImage(isFirstImage ? "YES" : "NO");
                images.add(image);
                isFirstImage = false;
            }
            getRepository().saveAll(images);
            return images;
        }
        private T uploadS3(MultipartFile file, E entity) throws IOException {
            String fileName = generateFileName(file, entity);
            s3.upload(file ,fileName);
            return  imageBuild(entity, file, fileName);
        }
    }
}


