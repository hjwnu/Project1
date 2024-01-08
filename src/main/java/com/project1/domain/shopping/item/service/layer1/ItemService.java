package com.project1.domain.shopping.item.service.layer1;

import com.project1.domain.shopping.item.service.layer2.ItemImageService;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.domain.shopping.item.dto.ItemDto;
import com.project1.domain.shopping.item.dto.ItemImageResponseDto;
import com.project1.domain.shopping.item.dto.ItemSearchCondition;
import com.project1.domain.shopping.item.dto.OnlyItemResponseDto;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.entity.ItemImage;
import com.project1.domain.shopping.item.service.layer2.ItemCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemImageService itemImageService;
    public final ItemCrudService crudService;

     public ItemService(ItemImageService itemImageService, ItemCrudService crudService) {
         this.itemImageService = itemImageService;
         this.crudService = crudService;
     }

     /*  일반 유저 - 상품 조회 */
    @Transactional
    public ItemDto.Response findItem(long itemId) {
        Item item = crudService.findEntity(itemId);
        return crudService.entityToResponse(item);
    }
    @Transactional
    public Page<OnlyItemResponseDto> findItems(int page, List<ItemSearchCondition> conditions){
        PageRequest pageRequest = PageRequest.of(page - 1, 9);
        List<OnlyItemResponseDto> result = crudService.conditionSearch(page, conditions);
        setImages(result);
        return new PageImpl<>(result, pageRequest, result.size());
    }

    /* 관리자 - 상품 등록 및 수정, 삭제 */
    @Transactional
    public ItemDto.Response createItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        verifySameItemNameExist(requestBody.getName());

        Item item = crudService.create(requestBody);   // 상품 저장 후, 상품에 매핑된 이미지 저장하는 순서.

        createItemImage(itemImgFileList, item);

        return crudService.entityToResponse(item);
    }
    @Transactional
    public ItemDto.Response updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        Item findItem = crudService.findEntity(requestBody.getItemId());
        Item updatedItem =crudService.update(requestBody.getItemId(),requestBody);
        itemImageService.update(itemImgFileList, findItem, updatedItem);
        return crudService.entityToResponse(updatedItem);
    }
    @Transactional
    public void deleteItem(long id) {
       Item item = crudService.findEntity(id);
       itemImageService.delete(item);
       crudService.delete(id);
    }


    /* private 메서드 */
    private void verifySameItemNameExist(String name) {
        if (crudService.verifyExistItemName(name) != null ) {
            throw new BusinessLogicException(ExceptionCode.ITEM_EXIST);
        }
    }
    private void createItemImage(List<MultipartFile> itemImgFileList, Item item) throws IOException {
        List<ItemImage> images = itemImageService.saveAll(itemImgFileList, item);
        item.setImages(images);
    }
    private void setImages(List<OnlyItemResponseDto> result) {
        List<Long> itemIds = result.stream().map(OnlyItemResponseDto::getItemId).collect(Collectors.toList());
        Map<Long, List<ItemImageResponseDto>> imagesMap = itemImageService.fetchImages(itemIds);

        result.forEach(item -> item.setImageURLs(imagesMap.get(item.getItemId())));
    }
}
