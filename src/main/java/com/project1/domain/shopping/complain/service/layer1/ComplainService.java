package com.project1.domain.shopping.complain.service.layer1;

import com.project1.domain.shopping.complain.dto.ComplainDto;
import com.project1.domain.shopping.complain.entity.Complain;
import com.project1.domain.shopping.complain.service.layer2.ComplainCrudService;
import com.project1.domain.member.entity.Member;
import com.project1.domain.member.service.Layer2.MemberVerificationService;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.service.layer2.ItemCrudService;
import org.springframework.stereotype.Service;

@Service
public class ComplainService {
    private final ComplainCrudService crudService;
    private final ItemCrudService itemCrudService;
    private final MemberVerificationService memberVerificationService;

    public ComplainService(ComplainCrudService crudService, ItemCrudService itemCrudService, MemberVerificationService memberVerificationService) {
        this.crudService = crudService;
        this.itemCrudService = itemCrudService;
        this.memberVerificationService = memberVerificationService;
    }
    public ComplainDto.ComplainResponseDto createComplain(ComplainDto.ComplainPostDto complainPostDto) {

        Complain complain = crudService.create(complainPostDto);
        Item item = itemCrudService.findEntity(complainPostDto.getItemId());
        Member member = memberVerificationService.findTokenMember();

        complain.setMember(member);
        complain.setItem(item);

        return crudService.entityToResponse(crudService.save(complain));
    }
}
