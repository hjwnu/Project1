package com.project1.global.generic;

import com.project1.global.utils.S3;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface GenericImageService<E, T extends GenericImage, R> {
    @Transactional
    void update(List<MultipartFile> imgFileList, E findEntity, E updatedEntity) throws IOException;
    List<T> saveAll(List<MultipartFile> imgFileList, E entity) throws IOException ;
    void delete(E e);
    Map<Long, List<R>> fetchImages(List<Long> ids);
    @Transactional
    abstract class GenericImageServiceImpl<E, T extends GenericImage, R> implements GenericImageService<E,T,R>{
        protected final S3 s3;
        public GenericImageServiceImpl(S3 s3) {
            this.s3 = s3;
        }
        protected abstract JpaRepository<T, Long> getRepository();
        protected abstract R imageToResponse(T t);
        protected abstract List<T> getImages(E e);
        protected abstract void setImages(E entity, List<T> images);
        protected abstract T imageBuild(E e, MultipartFile file, String name);
        @NotNull
        protected abstract String generateFileName(MultipartFile file, E e);
        protected abstract Map<Long, List<T>> fetch(List<Long> ids);
        public Map<Long, List<R>> fetchImages(List<Long> ids) {
            Map<Long, List<T>> imageMap = fetch(ids);
            return imageMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry<Long,List<T>>::getKey,
                            entry -> entry.getValue().stream()
                                    .map(this::imageToResponse)
                                    .collect(Collectors.toList())
                    ));
        }
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


