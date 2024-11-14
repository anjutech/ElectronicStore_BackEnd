package com.ElectronicStore.Controllers;

import com.ElectronicStore.dtos.ApiResponseMessage;
import com.ElectronicStore.dtos.CreateOrderRequest;
import com.ElectronicStore.dtos.OrderDto;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.service.OrderService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

       @PostMapping(value = "/create")
//    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) throws Exception {

        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .status(HttpStatus.OK)
                .message("Order is Removed !!")
                .success(true)
                .build();

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId) {
        List<OrderDto> orderOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(orderOfUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        PageableResponse<OrderDto> orderOfUser = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orderOfUser, HttpStatus.OK);
    }
}

