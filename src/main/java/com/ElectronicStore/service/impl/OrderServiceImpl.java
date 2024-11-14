package com.ElectronicStore.service.impl;

import com.ElectronicStore.Exceptions.BadApiRequest;
import com.ElectronicStore.Exceptions.ResourceNotFoundException;
import com.ElectronicStore.dtos.CreateOrderRequest;
import com.ElectronicStore.dtos.OrderDto;
import com.ElectronicStore.dtos.PageableResponse;
import com.ElectronicStore.entities.*;
import com.ElectronicStore.helpers.Helper;
import com.ElectronicStore.repositories.CartRepository;
import com.ElectronicStore.repositories.OrderRepository;
import com.ElectronicStore.repositories.userRepo;
import com.ElectronicStore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private userRepo userRepo;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;


    //    /**
//     * @param orderDto
//     * @param userId
//     * @return
//     */
    @Override
//    public OrderDto createOrder(CreateOrderRequest orderDto) throws Exception {
//        String userId = orderDto.getUserId();
//        String cartId = orderDto.getCartId();
//
////
////        if (orderDto.getUserId() != null) {
////            Optional user = userRepo.findById(userId);
////        } else {
////            throw new Exception("User Not Find with Given Email !!");
////        }
////        if (orderDto.getCartId() != null) {
////            Optional<Cart> cart = cartRepository.findById(cartId);
////        } else{
////            throw new Exception("User Not Find with Given Email !!");
////    }
//
//        user user;
//        user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Find with Given Email !!"));
//        Cart cart = cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart ID Not Found With This OrderEmail !!"));
//
//       List<CartItem> cartItems = cart.getItems();
//       if(cartItems.size() <= 0){
//           throw
//                    new BadApiRequest("Invalid Number Of Cart !");
//       }
//
//        Order order = Order.builder().
//                billingName(orderDto.getBillingName())
//                .billingPhone(orderDto.getBillingPhone())
//                .billingAddress(orderDto.getBillingAddress())
//                .orderedDate(new Date())
//                .deliverDate(null)
//                .paymentStatus(orderDto.getPaymentStatus())
//                .orderStatus(orderDto.getOrderStatus())
//                .orderId(UUID.randomUUID().toString())
//                .user(user).build();
//
//        AtomicReference <Integer> orderAmount = new AtomicReference<>(0);
//       List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
//
//           OrderItem orderItem = OrderItem.builder()
//                   .quantity(cartItem.getQuantity())
//                   .product(cartItem.getProduct())
//                   .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
//                   .order(order)
//                   .build();
//          orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
//           return  orderItem;
//       }).collect(Collectors.toList());
//
//       order.setOrderItems(orderItems);
//       order.setOrderAmount(orderAmount.get());
//
//       cart.getItems().clear();
//       cartRepository.save(cart);
//       Order savedOrder = orderRepository.save(order);
//        return modelMapper.map(savedOrder, OrderDto.class);
//    }

    public OrderDto createOrder(CreateOrderRequest orderDto) throws Exception {
        // Validate required fields
        if (orderDto.getUserId() == null || orderDto.getUserId().isEmpty()) {
            throw new BadApiRequest("User Id is required !!");
        }
        if (orderDto.getCartId() == null || orderDto.getCartId().isEmpty()) {
            throw new BadApiRequest("Cart Id is required !!");
        }
        if (orderDto.getBillingAddress() == null || orderDto.getBillingAddress().isEmpty()) {
            throw new BadApiRequest("Billing Address is required !!");
        }
        if (orderDto.getBillingName() == null || orderDto.getBillingName().isEmpty()) {
            throw new BadApiRequest("Billing Name is required !!");
        }
        if (orderDto.getBillingPhone() == null || orderDto.getBillingPhone().isEmpty()) {
            throw new BadApiRequest("Phone Number is required !!");
        }

        // Extract user and cart details
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();

        // Fetch user and cart, throw custom exceptions if not f
       user  user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with Given Id !!"));
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart ID Not Found !!"));

        // Validate cart items
        List<CartItem> cartItems = cart.getItems();
        if (cartItems.isEmpty()) {
            throw new BadApiRequest("Invalid Number Of Cart Items!");
        }

        // Build order object
        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliverDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        // Calculate total order amount
        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();
            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        // Set order items and order amount
        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        // Clear cart after order creation
        cart.getItems().clear();
        cartRepository.save(cart);

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Map the saved order to OrderDto and return
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    /**
     * @param orderId
     */
    @Override
    public void removeOrder(String orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Resource Not found !"));
    orderRepository.delete(order);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        user user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Resource Not Found !!"));
       List<Order> orders = orderRepository.findByUser(user);
       List<OrderDto> orderDtos = orders.stream().map(order->modelMapper.map(order , OrderDto.class )).collect(Collectors.toList());

        return orderDtos ;
    }

    /**
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @Override
    public PageableResponse<OrderDto> getOrders( int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPegebaleResponse(page, OrderDto.class);
    }
}
