package com.ElectronicStore.service;

import com.ElectronicStore.dtos.CreateOrderRequest;
import com.ElectronicStore.dtos.OrderDto;
import com.ElectronicStore.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

OrderDto createOrder(CreateOrderRequest request) throws Exception;

void removeOrder(String orderId);

List<OrderDto> getOrdersOfUser(String userId);

PageableResponse<OrderDto> getOrders( int pageNumber, int pageSize,
                                     String sortBy, String sortDir);


}
