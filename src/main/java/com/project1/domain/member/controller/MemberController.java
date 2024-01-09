package com.project1.domain.member.controller;

import com.project1.domain.member.dto.MemberPatchDto;
import com.project1.domain.member.dto.MemberPostDto;
import com.project1.domain.member.dto.MemberResponseDto;
import com.project1.domain.member.dto.PasswordPatchDto;
import com.project1.domain.member.service.Layer1.GetMineService;
import com.project1.domain.member.service.Layer1.MemberService;
import com.project1.domain.notice.comment.dto.CommentDto;
import com.project1.domain.notice.comment.entity.Comment;
import com.project1.domain.shopping.complain.dto.ComplainDto;
import com.project1.domain.shopping.complain.entity.Complain;
import com.project1.domain.shopping.review.dto.ReviewDto;
import com.project1.domain.shopping.review.entity.Review;
import com.project1.global.response.MultiResponseDto;
import com.project1.global.response.SingleResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final GetMineService getMineService;
    //회원가입
    @PostMapping("/new")
    @ApiOperation(value = "회원 가입 API", notes = "이메일을 아이디로 사용하여 가입")
    public ResponseEntity<SingleResponseDto<MemberResponseDto>> postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        MemberResponseDto savedMember = memberService.createMember(memberPostDto);
        SingleResponseDto<MemberResponseDto> responseDto = new SingleResponseDto<>(savedMember, HttpStatus.CREATED);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //회원탈퇴
    @DeleteMapping("/myPage")
    @ApiOperation(value = "회원 탈퇴 API", notes = "로그인 검증을 통해 회원 탈퇴")
    public ResponseEntity<HttpStatus> deleteMember(@RequestBody String email,@RequestBody String password){
        memberService.deleteMember(email, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 회원 개인정보(주소,전화번호) 수정
    @PatchMapping("/profile")
    public ResponseEntity<SingleResponseDto<MemberResponseDto>> patchProfile(@RequestBody MemberPatchDto requestBody) {
        MemberResponseDto updatedMember = memberService.updateProfile(requestBody);

        SingleResponseDto<MemberResponseDto> response = new SingleResponseDto<>(updatedMember, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    //회원 비밀번호 수정
    @PatchMapping("/password")
    public ResponseEntity<SingleResponseDto<String>> patchPassword(@RequestBody PasswordPatchDto requestBody) {
        memberService.changePassword(requestBody);
        SingleResponseDto<String>response = new SingleResponseDto<>("비밀번호 변경 성공", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    // 회원 정보 조회
    @GetMapping("/myPage")
    public ResponseEntity<SingleResponseDto<MemberResponseDto>> getMember() {
        MemberResponseDto response = memberService.getProfile();
        SingleResponseDto<MemberResponseDto> responseDto = new SingleResponseDto<>(response,HttpStatus.OK);
        return ResponseEntity.ok(responseDto);
    }
    @PostMapping("/search")
    public ResponseEntity<SingleResponseDto<MemberResponseDto>> getMember(@RequestParam("name") String name) {
        MemberResponseDto response = memberService.getOtherProfile(name);
        SingleResponseDto<MemberResponseDto> responseDto = new SingleResponseDto<>(response,HttpStatus.OK);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/myReview")
    public ResponseEntity<MultiResponseDto<ReviewDto.ReviewResponseDto>> getMyReviews(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        Page<?> myReviews = getMineService.getMine(page, size, Review.class);
        MultiResponseDto<ReviewDto.ReviewResponseDto> response = (MultiResponseDto<ReviewDto.ReviewResponseDto>) new MultiResponseDto<>(myReviews, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/myComment")
    public ResponseEntity<MultiResponseDto<CommentDto.ResponseDto>> getMyComments(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        Page<?> myComments = getMineService.getMine(page,size, Comment.class);
        MultiResponseDto<CommentDto.ResponseDto> response = (MultiResponseDto<CommentDto.ResponseDto>) new MultiResponseDto<>(myComments, HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/myComplain")
    public ResponseEntity<MultiResponseDto<ComplainDto.ComplainResponsesDto>> getMyComplains(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        Page<?> myComplains = getMineService.getMine(page,size, Complain.class);
        MultiResponseDto<ComplainDto.ComplainResponsesDto> response = (MultiResponseDto<ComplainDto.ComplainResponsesDto>) new MultiResponseDto<>(myComplains, HttpStatus.OK);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}