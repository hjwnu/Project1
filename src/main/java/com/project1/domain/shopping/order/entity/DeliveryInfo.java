package com.project1.domain.shopping.order.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter@Setter
@Entity
@NoArgsConstructor
@Builder @AllArgsConstructor
public class DeliveryInfo {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long deliveryId;
//    @NotNull
//    @Size(min = 2, max = 10)
    private String recipient;
//    @NotNull
//    @Pattern(regexp = "01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String phone;
    private String address;

    public DeliveryInfo createDeliverInfo(String recipient, String phone, String address) {
        this.address = address;
        this.phone= phone;
        this.recipient = recipient;

        return this;
    }
}