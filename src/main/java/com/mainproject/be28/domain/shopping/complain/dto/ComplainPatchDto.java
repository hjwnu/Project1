package com.mainproject.be28.domain.shopping.complain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComplainPatchDto {
            private Long complainId;
            private String title;

            private String content;

        }


