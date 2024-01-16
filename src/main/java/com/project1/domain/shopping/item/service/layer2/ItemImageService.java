package com.project1.domain.shopping.item.service.layer2;

import com.project1.domain.shopping.item.repository.ItemImageRepository;
import com.project1.domain.shopping.item.dto.ItemImageDto;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.entity.ItemImage;
import com.project1.domain.shopping.item.mapper.ItemMapper;
import com.project1.global.generic.GenericImageService;
import com.project1.global.utils.S3;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ItemImageService extends GenericImageService.GenericImageServiceImpl<Item, ItemImage, ItemImageDto> {
    private final ItemMapper mapper;
    private final ItemImageRepository repository;
    private final String PATH = "images/item/";
    public ItemImageService(S3 S3, ItemImageRepository repository, ItemMapper mapper) {
        super(S3);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    protected JpaRepository<ItemImage, Long> getRepository() {
        return repository;
    }

    @Override
    protected ItemImageDto imageToResponse(ItemImage itemImage) {
        return mapper.imageToResponse(itemImage);
    }

    @Override
    protected List<ItemImage> getImages(Item item) {
        return item.getImages();
    }

    @Override
    protected void setImages(Item entity, List<ItemImage> images) {
        entity.setImages(images);
    }

    @Override
    protected ItemImage imageBuild(Item item, MultipartFile file, String name) {
        return ItemImage.builder().item(item)
                .originalName(file.getOriginalFilename())
                .imageName(name)
                .path(PATH)
                .build();
    }

    @Override
    protected @NotNull String generateFileName(MultipartFile file, Item item) {
        return PATH +
                item.getCategory().toUpperCase() +
                "_" +
                UUID.randomUUID().toString().substring(0, 10) +
                "_" +
                file.getOriginalFilename();
    }
    @Override
    protected Map<Long, List<ItemImage>> fetch(List<Long> ids) {
        return repository.fetchItemImages(ids);
    }
}
