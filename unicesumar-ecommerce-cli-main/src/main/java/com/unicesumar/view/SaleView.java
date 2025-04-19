package com.unicesumar.view;

import com.unicesumar.paymentMethods.PaymentType;

import java.util.Scanner;

public class SaleView {
    private final Scanner scanner = new Scanner(System.in);

    public String getUserEmail() {
        System.out.print("Digite o Email do usuário: ");
        return scanner.nextLine();
    }

    public String getProductIds() {
        System.out.print("Digite os IDs dos produtos (separados por vírgula): ");
        return scanner.nextLine();
    }

    public PaymentType getPaymentType() {
        System.out.println("Escolha a forma de pagamento:");
        System.out.println("1 - Cartão de Crédito");
        System.out.println("2 - Boleto");
        System.out.println("3 - PIX");
        System.out.print("Opção: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                return PaymentType.CARTAO;
            case 2:
                return PaymentType.BOLETO;
            case 3:
                return PaymentType.PIX;
            default:
                System.out.println("Opção inválida. Usando PIX como padrão.");
                return PaymentType.PIX;
        }
    }

    public void showSaleSummary(String userName, String productList, double total, PaymentType paymentType) {
        System.out.println("\nResumo da venda:");
        System.out.println("Cliente: " + userName);
        System.out.println("Produtos:");
        System.out.println(productList);
        System.out.printf("Valor total: R$ %.2f%n", total);
        System.out.println("Pagamento: " + paymentType);
        System.out.println("Venda registrada com sucesso!");
    }
}