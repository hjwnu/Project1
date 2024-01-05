package com.project1.domain.shopping.complain.service.layer1;

import com.project1.domain.shopping.complain.dto.ComplainPostDto;
import com.project1.domain.shopping.complain.dto.ComplainResponseDto;
import com.project1.domain.shopping.complain.entity.Complain;
import com.project1.domain.shopping.complain.service.layer2.ComplainCrudService;
import com.project1.domain.member.entity.Member;
import com.project1.domain.member.service.Layer2.MemberVerifyService;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.service.layer2.ItemCrudService;
import org.springframework.stereotype.Service;

@Service
public class ComplainService {
    private final ComplainCrudService crudService;
    private final ItemCrudService itemCrudService;
    private final MemberVerifyService memberVerifyService;

    public ComplainService(ComplainCrudService crudService, ItemCrudService itemCrudService, MemberVerifyService memberVerifyService) {
        this.crudService = crudService;
        this.itemCrudService = itemCrudService;
        this.memberVerifyService = memberVerifyService;
    }
    public ComplainResponseDto createComplain(ComplainPostDto complainPostDto) {

        Complain complain = crudService.create(complainPostDto);
        Item item = itemCrudService.findEntity(complainPostDto.getItemId());
        Member member = memberVerifyService.findTokenMember();

        complain.setMember(member);
        complain.setItem(item);

        return crudService.entityToResponse(crudService.save(complain));
    }
}