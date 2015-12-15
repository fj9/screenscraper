package com.junipernine.screenscraping;

import java.util.Scanner;

/**
 * Created on 14/12/2015.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("************************************");
        System.out.println("* Welcome to the Net-A-Porter Shop *");
        System.out.println("************************************");
        System.out.println("Enter \"Q\" to Quit");
        System.out.println("Enter \"add <ProductId>\" to add to basket");
        System.out.println("Enter \"remove <ProductId>\" to remove from basket");
        System.out.println("Enter \"list\" to show a list of products in the inventory");
        System.out.println("Enter \"total\" to show the total price of the basket");

//        NetAPorterShop shop = new NetAPorterShop();
//        shop.loadProducts();
//
//        while (true) {
//            String inputValue = scanner.next();
//
//            if (inputValue.startsWith("add")) {
//                shop.addProductToBasket(scanner.next());
//
//            } else if (inputValue.startsWith("remove")) {
//                shop.removeProductFromBasket(scanner.next());
//            } else if (inputValue.startsWith("list")) {
//                shop.listProducts();
//
//            } else if (inputValue.startsWith("total")) {
//                shop.getTotal();
//
//            } else if ("Q".equalsIgnoreCase(inputValue)) {
//                System.out.println("Thanks for shopping at Net-a-Porter!");
//                System.exit(0);
//            }
//        }
    }
}