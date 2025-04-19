package com.unicesumar.controller;

import com.unicesumar.PaymentManager;
import com.unicesumar.PaymentMethodFactory;
import com.unicesumar.entities.Product;
import com.unicesumar.entities.User;
import com.unicesumar.paymentMethods.PaymentType;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.UserRepository;
import com.unicesumar.view.SaleView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SaleController {
    private final SaleView view;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PaymentManager paymentManager;

    public SaleController(Connection connection) {
        this.view = new SaleView();
        this.userRepository = new UserRepository(connection);
        this.productRepository = new ProductRepository(connection);
        this.paymentManager = new PaymentManager();
    }

    public void registerSale() {
        String email = view.getUserEmail();
        Optional<User> userOpt = userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        if (userOpt.isEmpty()) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        User user = userOpt.get();

        String productIdsInput = view.getProductIds();
        String[] productIds = productIdsInput.split(",");
        List<Product> products = new ArrayList<>();
        double total = 0;

        for (String id : productIds) {
            Optional<Product> productOpt = productRepository.findById(UUID.fromString(id.trim()));
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                products.add(product);
                total += product.getPrice();
            } else {
                System.out.println("Produto com ID " + id + " não encontrado.");
            }
        }

        if (products.isEmpty()) {
            System.out.println("Nenhum produto válido foi selecionado.");
            return;
        }

        PaymentType paymentType = view.getPaymentType();
        paymentManager.setPaymentMethod(PaymentMethodFactory.create(paymentType));
        paymentManager.pay(total);

        StringBuilder productList = new StringBuilder();
        for (Product product : products) {
            productList.append("- ").append(product.getName())
                    .append(" (R$ ").append(product.getPrice()).append(")\n");
        }
        view.showSaleSummary(user.getName(), productList.toString(), total, paymentType);
    }
}