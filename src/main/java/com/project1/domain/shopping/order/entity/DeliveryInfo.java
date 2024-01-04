package com.project1.domain.shopping.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter@Setter
@Entity
@NoArgsConstructor
public class DeliveryInfo {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long deliveryId;
//    @NotNull
//    @Size(min = 2, max = 10)
    private String recipient;
//    @NotNull
//    @Pattern(regexp = "01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String phoneNumber;
    private String address;

    public DeliveryInfo createDeliverInfo(String recipient, String phoneNumber, String address) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.recipient = recipient;

        return this;
    }
}