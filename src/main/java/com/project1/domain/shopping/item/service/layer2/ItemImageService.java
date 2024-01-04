package com.project1.domain.shopping.item.service.layer2;

import com.project1.domain.shopping.item.repository.ItemImageRepository;
import com.project1.domain.shopping.item.dto.ItemImageResponseDto;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.entity.ItemImage;
import com.project1.domain.shopping.item.mapper.ItemMapper;
import com.project1.global.utils.S3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ItemImageService {
    private final S3 S3;
    private final ItemImageRepository repository;
    private final ItemMapper mapper;

    public ItemImageService(S3 S3, ItemImageRepository repository, ItemMapper mapper) {
        this.S3 = S3;
        this.repository = repository;
        this.mapper = mapper;
    }

    public Map<Long,List<ItemImageResponseDto>> fetchItemImages(List<Long> itemIds){
        Map<Long,List<ItemImage>> imageMap = repository.fetchItemImages(itemIds);
        return imageMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry<Long,List<ItemImage>>::getKey,
                        entry -> entry.getValue().stream()
                                .map(mapper::imageToResponse)
                                .collect(Collectors.toList())
                ));
    }
    public ItemImage uploadImage(MultipartFile file, Item item) throws IOException {
        StringBuilder fileNameBuilder = new StringBuilder();
        String path = "image/";
        fileNameBuilder
                .append(path)
                .append(item.getCategory().toUpperCase())
                .append("_")
                .append(UUID.randomUUID().toString(), 0, 10)
                .append("_")
                .append(file.getOriginalFilename());
        String fileName = fileNameBuilder.toString();
        String savedPath = S3.upload(file, fileName);
        log.info("Saved Path : "+savedPath);

        return ItemImage.builder().item(item)
                .originalName(file.getOriginalFilename())
                .imageName(fileName)
                .path(path)
                .build();
    }

    public void deleteImage(Item item) {
        List<ItemImage> images = item.getImages();
        for (ItemImage image : images) {
            S3.deleteFile(image.getImageName());
        }
    }

    public List<ItemImage> saveImage(Item item, List<MultipartFile> itemImgFileList) throws IOException {
        List<ItemImage> images = new ArrayList<>();
        for (int i = 0; i < itemImgFileList.size(); i++) {
            MultipartFile file = itemImgFileList.get(i);
            ItemImage image = uploadImage(file, item);
            if (i == 0) {
                image.setRepresentationImage("YES");
            } else {
                image.setRepresentationImage("NO");
            }
            images.add(image);
        }
        return images;
    }

    public List<ItemImage> saveImages(List<MultipartFile> itemImgFileList, Item item) throws IOException {
        List<ItemImage> images = new ArrayList<>();
        if (itemImgFileList != null){
            images = saveImage(item, itemImgFileList);
        }
        return images;
    }

    public void saveImages(List<ItemImage> images) {
        if(images.size()>0){
            repository.saveAll(images);}
    }
    @Transactional
    public void updateImages(List<MultipartFile> itemImgFileList, Item findItem, Item updatedItem) throws IOException {

        //새로운 파일 이미지가 있다면, 새로 추가
        if (itemImgFileList != null) {
            for (MultipartFile image : itemImgFileList) {
                ItemImage img = uploadImage(image, updatedItem);
                updatedItem.addImage(img);
            }
            updatedItem.setRepresentationImage();
            repository.saveAll(updatedItem.getImages());
        }
    }


}
