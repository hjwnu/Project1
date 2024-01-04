package com.mainproject.be28.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table
 public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public Member(String email) {
        this.email = email;
    }


    public Member() {

    }

        // 회원 상태
//    @Column(nullable = false)
//    private MemberStatus status;
//
//    public enum MemberStatus {
//        ACTIVE,    // 활성 상태
//        SUSPENDED, // 정지 상태
//        INACTIVE,  // 비활성 상태
//        DELETED    // 삭제 상태
//    }

//
//   @Column()
//    private Long reportCount;

}
