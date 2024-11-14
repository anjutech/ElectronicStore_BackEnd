package com.ElectronicStore.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {
//    @NotBlank(message = "Cart Id Is Required !!")
    private String cartId;

//    @NotBlank(message = "User Id Is Required !!")
    private String userId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";

//    @NotBlank(message = "Billing Address Is Required !!")
    private String billingAddress;

//    @NotBlank(message = "Phone Number Is Required !!")
    private String billingPhone;

//    @NotBlank(message = "Billing Name Is Required !!")
    private String billingName;


}
