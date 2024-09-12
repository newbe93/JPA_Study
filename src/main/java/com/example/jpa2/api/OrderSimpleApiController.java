package com.example.jpa2.api;

import com.example.jpa2.domain.Address;
import com.example.jpa2.domain.Order;
import com.example.jpa2.domain.OrderStatus;
import com.example.jpa2.repository.OrderRepository;
import com.example.jpa2.repository.OrderSearch;
import com.example.jpa2.repository.order.simplequery.OrderSimpleQueryDto;
import com.example.jpa2.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;
    /*
    * V2. 엔티티를 조회해서 DTO로 변환 (fetch join 사용 X)
    * - 단점 : 지연로딩으로 쿼리 N번 호출
    **/
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        // ORDER 2개
        // N + 1
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
        List<OrderSimpleQueryDto> orders = orderSimpleQueryRepository.findOrderDtos();
        return orders;
    }

    @Data
    @AllArgsConstructor
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();

        }
    }
}
