package ordersService.service;

import lombok.extern.slf4j.Slf4j;
import ordersService.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    private final KafkaTemplate<String, Order>kafkaTemplate;
    private final String topic;

    public OrderService(KafkaTemplate<String, Order> kafkaTemplate, @Value("${app.kafka.newOrdersTopic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void placeOrder(Order order) {
        log.info("Placing order: {}", order);
        order.setStatus("NEW");
        kafkaTemplate.send(topic, order);
    }
}
