package com.project1.domain.shopping.complain.controller;

import com.project1.domain.shopping.complain.dto.*;
import com.project1.domain.shopping.complain.service.layer2.ComplainCrudService;
import com.project1.global.response.SingleResponseDto;
import com.project1.domain.shopping.complain.service.layer1.ComplainService;
import com.project1.global.response.MultiResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/complain")
public class ComplainController {

    private final ComplainService complainService;
    private final ComplainCrudService crudService;
    private  final HttpStatus ok = HttpStatus.OK;

    public ComplainController(ComplainService complainService, ComplainCrudService crudService) {
        this.complainService = complainService;
        this.crudService = crudService;

    }

    @PostMapping ("/new")
    public ResponseEntity<SingleResponseDto<ComplainDto.ComplainResponseDto>> postComplain(@RequestBody @Valid ComplainDto.ComplainPostDto complainPostDto) {
        ComplainDto.ComplainResponseDto response = complainService.createComplain(complainPostDto);
        SingleResponseDto<ComplainDto.ComplainResponseDto> responses =  new SingleResponseDto<>(response,ok);
        return new ResponseEntity<>(responses, ok);
    }
    @GetMapping ("{complain-id}")
    public ResponseEntity<SingleResponseDto<ComplainDto.ComplainResponseDto>> getComplain(@PathVariable("complain-id") @Positive long complainId) {
        ComplainDto.ComplainResponseDto response = crudService.getResponse(complainId);
        SingleResponseDto<ComplainDto.ComplainResponseDto> responses =  new SingleResponseDto<>(response,ok);
        return new ResponseEntity<>(responses, ok);
    }
    @GetMapping("")
    public ResponseEntity<MultiResponseDto<ComplainDto.ComplainResponsesDto>> getComplains(@RequestParam(name = "page", defaultValue = "1") int page,
                                                                                           @RequestParam(name = "size", defaultValue = "10") int size){
        Page<ComplainDto.ComplainResponsesDto> pageComplains = crudService.findAll(page,size);
        MultiResponseDto<ComplainDto.ComplainResponsesDto> response = new MultiResponseDto<>(pageComplains, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PatchMapping("/{complain-id}")
    public ResponseEntity<SingleResponseDto<ComplainDto.ComplainResponseDto> > patchComplain(@PathVariable("complain-id") @Positive long complainId,
                                                                                             @Valid @RequestBody ComplainDto.ComplainPatchDto complainPatchDto){
        ComplainDto.ComplainResponseDto response = crudService.entityToResponse(crudService.update(complainId, complainPatchDto));

        SingleResponseDto<ComplainDto.ComplainResponseDto> responses =  new SingleResponseDto<>(response,ok);
        return new ResponseEntity<>(responses, ok);
    }
    @DeleteMapping("/{complain-id}")
    public ResponseEntity<HttpStatus> deleteComplain(@PathVariable("complain-id") @Positive long complainId){
        crudService.delete(complainId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


