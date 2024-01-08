package com.project1.domain.shopping.item.service.layer2;

import com.project1.domain.shopping.item.repository.ItemImageRepository;
import com.project1.domain.shopping.item.dto.ItemImageResponseDto;
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
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ItemImageService extends GenericImageService.GenericImageServiceImpl<Item, ItemImage, ItemImageResponseDto> {
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
    protected List<ItemImage> getImages(Item item) {
        return item.getImages();
    }

    @Override
    protected void setImages(Item entity, List<ItemImage> images) {
        entity.setImages(images);
    }


    @Override
    public Map<Long, List<ItemImageResponseDto>> fetchImages(List<Long> ids) {
        Map<Long, List<ItemImage>> imageMap = fetch(ids);
        return imageMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry<Long,List<ItemImage>>::getKey,
                        entry -> entry.getValue().stream()
                                .map(mapper::imageToResponse)
                                .collect(Collectors.toList())
                ));
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
        return new StringBuilder()
                .append(PATH)
                .append(item.getCategory().toUpperCase())
                .append("_")
                .append(UUID.randomUUID().toString(), 0, 10)
                .append("_")
                .append(file.getOriginalFilename())
                .toString();
    }
    @Override
    protected Map<Long, List<ItemImage>> fetch(List<Long> ids) {
        return repository.fetchItemImages(ids);
    }
}
