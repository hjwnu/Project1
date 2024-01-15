package com.project1.domain.shopping.item.service.layer1;

import com.project1.domain.shopping.item.service.layer2.ItemImageService;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.domain.shopping.item.dto.ItemDto;
import com.project1.domain.shopping.item.dto.ItemImageDto;
import com.project1.domain.shopping.item.dto.ItemSearchCondition;
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
    public ItemDto.ResponseWithReview findItem(long itemId) {
        Item item = crudService.findEntity(itemId);
        return crudService.entityToResponse(item);
    }
    @Transactional
    public Page<ItemDto.ResponseDtoWithoutReview> findItems(int page, ItemSearchCondition conditions){
        PageRequest pageRequest = PageRequest.of(page - 1, 9);
        List<ItemDto.ResponseDtoWithoutReview> result = crudService.conditionSearch(page, conditions);
        setImages(result);
        return new PageImpl<>(result, pageRequest, result.size());
    }

    /* 관리자 - 상품 등록 및 수정, 삭제 */
    @Transactional
    public ItemDto.ResponseWithReview createItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        verifySameItemNameExist(requestBody.getName());
        Item item = crudService.create(requestBody);   // After save Item, save Item Image mapped by item.
        createItemImage(itemImgFileList, item);
        return crudService.entityToResponse(item);
    }
    @Transactional
    public ItemDto.ResponseWithReview updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException {
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
    private void setImages(List<ItemDto.ResponseDtoWithoutReview> result) {
        List<Long> itemIds = result.stream().map(ItemDto.ResponseDtoWithoutReview::getItemId).collect(Collectors.toList());
        Map<Long, List<ItemImageDto>> imagesMap = itemImageService.fetchImages(itemIds);

        result.forEach(item -> item.setImageURLs(imagesMap.get(item.getItemId())));
    }
}
