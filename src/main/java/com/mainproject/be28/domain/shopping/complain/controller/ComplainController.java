package com.mainproject.be28.domain.shopping.complain.controller;

import com.mainproject.be28.domain.shopping.complain.dto.ComplainPatchDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainPostDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponsesDto;
import com.mainproject.be28.domain.shopping.complain.service.layer1.ComplainService;
import com.mainproject.be28.domain.shopping.complain.service.layer2.ComplainCrudService;
import com.mainproject.be28.global.response.MultiResponseDto;
import com.mainproject.be28.global.response.SingleResponseDto;
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
    public ResponseEntity<SingleResponseDto<ComplainResponseDto>> postComplain(@RequestBody @Valid ComplainPostDto complainPostDto) {
        ComplainResponseDto response = complainService.createComplain(complainPostDto);
        SingleResponseDto<ComplainResponseDto> responses =  new SingleResponseDto<>(response,ok);
        return new ResponseEntity<>(responses, ok);
    }
    @GetMapping ("{complain-id}")
    public ResponseEntity<SingleResponseDto<ComplainResponseDto>> getComplain(@PathVariable("complain-id") @Positive long complainId) {
        ComplainResponseDto response = crudService.getResponse(complainId);
        SingleResponseDto<ComplainResponseDto> responses =  new SingleResponseDto<>(response,ok);
        return new ResponseEntity<>(responses, ok);
    }
    @GetMapping("")
    public ResponseEntity<MultiResponseDto<ComplainResponsesDto>> getComplains(@RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "size", defaultValue = "10") int size){
        Page<ComplainResponsesDto> pageComplains = crudService.findAll(page,size);
        MultiResponseDto<ComplainResponsesDto> response = new MultiResponseDto<>(pageComplains, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PatchMapping("/{complain-id}")
    public ResponseEntity<SingleResponseDto<ComplainResponseDto> > patchComplain(@PathVariable("complain-id") @Positive long complainId,
                                    @Valid @RequestBody ComplainPatchDto complainPatchDto){
        ComplainResponseDto response = crudService.entityToResponse(crudService.update(complainId, complainPatchDto));

        SingleResponseDto<ComplainResponseDto> responses =  new SingleResponseDto<>(response,ok);
        return new ResponseEntity<>(responses, ok);
    }
    @DeleteMapping("/{complain-id}")
    public ResponseEntity<HttpStatus> deleteComplain(@PathVariable("complain-id") @Positive long complainId){
        crudService.delete(complainId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }


