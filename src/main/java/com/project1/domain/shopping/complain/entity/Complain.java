package com.project1.domain.shopping.complain.entity;

import com.project1.global.generic.Auditable;
import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Complain extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long complainId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "COMPLAIN_STATUS")
    private ComplainStatus  complainStatus = ComplainStatus.COMPLAIN_EXIST;

    @Column(length = 20)
    private String title;

    @Column(length = 1000)
    private String content;



    public enum ComplainStatus {
        COMPLAIN_NOT_EXIST("존재하지 않는 문의사항"),
        COMPLAIN_EXIST("존재하는 문의사항");

        @Getter
        private String status;
        ComplainStatus(String status) {
            this.status = status;
        }
    }

}
