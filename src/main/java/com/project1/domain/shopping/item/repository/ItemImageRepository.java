package com.project1.domain.shopping.item.repository;

import com.project1.domain.shopping.item.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ItemImageRepository  extends JpaRepository<ItemImage, Long>,CustomImageRepository {

}
