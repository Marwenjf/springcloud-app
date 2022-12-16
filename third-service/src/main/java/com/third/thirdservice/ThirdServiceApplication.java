package com.third.thirdservice;

import com.third.thirdservice.entities.Bill;
import com.third.thirdservice.entities.ProductItem;
import com.third.thirdservice.model.Customer;
import com.third.thirdservice.model.Product;
import com.third.thirdservice.repositories.BillRepository;
import com.third.thirdservice.repositories.ProductItemRepository;
import com.third.thirdservice.service.CustomerRestClient;
import com.third.thirdservice.service.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class ThirdServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThirdServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient, ProductRestClient productRestClient){
        return args -> {
            Collection<Product> products = productRestClient.allProducts().getContent();
            Long customerId=1L;
            Customer customer = customerRestClient.findCustomerById(customerId);
            if(customer==null) throw new RuntimeException("Customer not found");
            Bill bill = new Bill();
            bill.setBillDate(new Date());
            bill.setCustomerId(customerId);
            Bill savedBill =  billRepository.save(bill);
            products.forEach(product -> {
                ProductItem productItem = new ProductItem();
                productItem.setBill(savedBill);
                productItem.setProductId(product.getId());
                productItem.setQuantity(1+new Random().nextInt(10));
                productItem.setPrice(product.getPrice());
                productItem.setDiscount(Math.random());
                productItemRepository.save(productItem);
            });
        };

    }

}
