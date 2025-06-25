import com.github.javafaker.Faker;
import entities.Customer;
import entities.Order;
import entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {

        Faker faker = new Faker(Locale.ENGLISH);
        Random rand = new Random();
        //  LocalDate orderDate = LocalDate.now().minusDays(rand.nextInt(1, 31)).minusMonths(rand.nextInt(1, 5)).minusYears(4);
        //  LocalDate deliveryDate = orderDate.plusDays(rand.nextInt(3, 8)).minusYears(4);
        LocalDate order1Date = LocalDate.of(2021, 2, 25);
        LocalDate order2Date = LocalDate.of(2022, 3, 6);
        LocalDate order3Date = LocalDate.of(2021, 3, 30);
        LocalDate order4Date = LocalDate.of(2021, 12, 25);
        LocalDate delivery1Date = order1Date.plusDays(3);
        LocalDate delivery2Date = order2Date.plusDays(3);
        LocalDate delivery3Date = order3Date.plusDays(3);
        LocalDate delivery4Date = order4Date.plusDays(3);

        LocalDate dayOne = LocalDate.of(2021, 2, 1);
        LocalDate lastDay = LocalDate.of(2021, 4, 1);

        Customer customer1 = new Customer(812903901, faker.leagueOfLegends().champion(), 2);
        Customer customer2 = new Customer(736453901, faker.leagueOfLegends().champion(), 1);
        Customer customer3 = new Customer(5872355, faker.leagueOfLegends().champion(), 3);
        Customer customer4 = new Customer(4587215, faker.leagueOfLegends().champion(), 2);

        Product book1 = new Product(1452365874, "That Hideous Strength", "Books", 50);
        Product book2 = new Product(1723423874, "A Song Of Ice and Fire", "Books", 178.50);
        Product book3 = new Product(1552365874, "Six of Crows", "Books", 101.29);
        Product book4 = new Product(345236584, "The Witcher", "Books", 55);

        Product boys1 = new Product(552365874, "Healing Potion", "Boys", 50);
        Product boys2 = new Product(952365874, "Skateboard", "Boys", 70.40);
        Product boys3 = new Product(1252365874, "Playstation 5", "Boys", 559.99);

        Product baby1 = new Product(65523674, "Diaper", "Baby", 20.50);
        Product baby2 = new Product(74523874, "Baby Bottle", "Baby", 40.12);
        Product baby3 = new Product(95865874, "Baby Oil", "Baby", 105);

        List<Product> productsInStock = new ArrayList<>();
        Collections.addAll(productsInStock, book1, book2, book3, book4, boys1, boys2, boys3, baby1, baby2, baby3);

        List<Product> itemsOrder1 = new ArrayList<>();
        Collections.addAll(itemsOrder1, book1, boys1, baby1);
        List<Product> itemsOrder2 = new ArrayList<>();
        Collections.addAll(itemsOrder2, book2, boys3, boys2);
        List<Product> itemsOrder3 = new ArrayList<>();
        Collections.addAll(itemsOrder3, book4, baby2, baby3);
        List<Product> itemsOrder4 = new ArrayList<>();
        Collections.addAll(itemsOrder4, book1, boys2);

        Order order1 = new Order(19293303, "Delivered", order1Date, delivery1Date, itemsOrder1, customer1);
        Order order2 = new Order(3654303, "Delivered Faster", order2Date, delivery2Date, itemsOrder2, customer2);
        Order order3 = new Order(984123, "Refunded", order3Date, delivery3Date, itemsOrder3, customer3);
        Order order4 = new Order(9244123, "Delivered on site", order4Date, delivery4Date, itemsOrder4, customer4);

        List<Order> orderList = new ArrayList<>();
        Collections.addAll(orderList, order1, order2, order3, order4);

        // -------------------------------- Exercise 1 -------------------------------

        Predicate<Product> isABook = product -> product.getCategory().equals("Books");
        Predicate<Product> isMoreThan100 = product -> product.getPrice() > 100;

        List<Product> booksCheaperThan100 = productsInStock.stream().filter(isABook.and(isMoreThan100)).toList();

        System.out.println(booksCheaperThan100);

        System.out.println("------------------------------------------------------------");

        // -------------------------------- Exercise 2 -------------------------------------------------

        List<Order> babyOrders = orderList.stream().filter(order -> order.getProducts().stream().anyMatch(product -> product.getCategory().equals("Baby"))).toList();

        System.out.println(babyOrders);

        System.out.println("------------------------------------------------------------");

        // -------------------------------- Exercise 3 -------------------------------------------------

        Predicate<Product> isBoys = product -> product.getCategory().equals("Boys");

        List<Product> discountedBoysItems = productsInStock.stream().filter(isBoys).map(product -> new Product(product.getId(), product.getName(), product.getCategory(), product.getPrice() * .9)).toList();

        System.out.println(discountedBoysItems);

        System.out.println("------------------------------------------------------------");

        // -------------------------------- Exercise 4 -------------------------------------------------

        Predicate<Order> isTier2 = order -> order.getCustomer().getTier() == 2 && order.getOrderDate().isAfter(dayOne.minusDays(1)) && order.getOrderDate().isBefore(lastDay.plusDays(1));

        List<Order> tier2Orders = orderList.stream().filter(isTier2).toList();
        //List<Order> specificOrders = tier2Orders.stream().filter(order -> order.getOrderDate().isAfter(dayOne.minusDays(1)) && order.getOrderDate().isBefore(lastDay.plusDays(1))).toList();
        List<Product> timeProducts = tier2Orders.stream().flatMap(order -> order.getProducts().stream()).toList();

        System.out.println(timeProducts);
    }
}