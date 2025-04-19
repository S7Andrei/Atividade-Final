package com.unicesumar;

import com.unicesumar.controller.SaleController;
import com.unicesumar.entities.Product;
import com.unicesumar.entities.User;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = null;
        UserRepository userRepository = null;

        Connection conn = null;

        // Parâmetros de conexão
        String url = "jdbc:sqlite:database.sqlite";

        // Tentativa de conexão
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                productRepository = new ProductRepository(conn);
                userRepository = new UserRepository(conn);
            } else {
                System.out.println("Falha na conexão.");
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n---MENU---");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Cadastrar Usuário");
            System.out.println("4 - Listar Usuários");
            System.out.println("5 - Registrar Venda");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Digite o nome do produto: ");
                    String productName = scanner.nextLine();
                    System.out.print("Digite o preço do produto: ");
                    double productPrice = scanner.nextDouble();
                    scanner.nextLine();
                    UUID productId = UUID.randomUUID();
                    Product newProduct = new Product(productId, productName, productPrice);

                    productRepository.save(newProduct);

                    System.out.println("Produto cadastrado com sucesso!");
                    System.out.println("ID do produto: " + productId.toString().substring(0, 8));
                    break;
                case 2:
                    System.out.println("Listar Produtos");
                    List<Product> products = productRepository.findAll();
                    products.forEach(product ->
                            System.out.println("ID: " + product.getUuid().toString().substring(0, 8) + ", Nome: " + product.getName() + ", Preço: R$ " + product.getPrice())
                    );
                    break;
                case 3:
                    System.out.print("Digite o nome do usuário: ");
                    String userName = scanner.nextLine();
                    System.out.print("Digite o email do usuário: ");
                    String userEmail = scanner.nextLine();
                    System.out.print("Digite a senha do usuário: ");
                    String userPassword = scanner.nextLine();
                    userRepository.save(new User(userName, userEmail, userPassword));
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;
                case 4:
                    System.out.println("Listar Usuários");
                    List<User> users = userRepository.findAll();
                    users.forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Registrar Venda");
                    SaleController saleController = new SaleController(conn);
                    saleController.registerSale();
                    break;
                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (option != 6);

        scanner.close();
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}