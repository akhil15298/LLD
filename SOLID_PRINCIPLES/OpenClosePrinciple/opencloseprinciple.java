package SOLID_PRINCIPLES.OpenClosePrinciple;

import java.util.ArrayList;
import java.util.List;

class product {
    private String name;
    private double price;

    public product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class shoppingCart {
    List<product> products;

    public shoppingCart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(product product) {
        products.add(product);
    }

    public double calculateTotal() {
        double total = 0;
        for (product product : products) {
            total += product.getPrice();
        }
        return total;
    }
}

class shoppingcartprinter {
    private shoppingCart cart;
    public void printReceipt(shoppingCart cart) {
        this.cart = cart;
        System.out.println("Receipt:");
        for (product product : cart.products) {
            System.out.println(product.getName() + ": $" + product.getPrice());
        }
        System.out.println("Total: $" + cart.calculateTotal());
    }
}

interface persistence{
    void save(shoppingCart cart);
}
class mysqlPersistence implements persistence {
    @Override
    public void save(shoppingCart cart) {
        // Code to save the shopping cart to a MySQL database
        System.out.println("Shopping cart saved to MySQL database.");
    }
}

class filePersistence implements persistence {
    @Override
    public void save(shoppingCart cart) {
        // Code to save the shopping cart to a file
        System.out.println("Shopping cart saved to a file.");
    }
}

class nosqlPersistence implements persistence {
    @Override
    public void save(shoppingCart cart) {
        // Code to save the shopping cart to a NoSQL database
        System.out.println("Shopping cart saved to NoSQL database.");
    }
}
public class opencloseprinciple {
    public static void main(String[] args) {
        shoppingCart cart = new shoppingCart();
        cart.addProduct(new product("Laptop", 999.99));
        cart.addProduct(new product("Mouse", 25.99));

        shoppingcartprinter printer = new shoppingcartprinter();
        printer.printReceipt(cart);

        persistence mysqlPersistence = new mysqlPersistence();
        mysqlPersistence.save(cart);

        persistence filePersistence = new filePersistence();
        filePersistence.save(cart);

        persistence nosqlPersistence = new nosqlPersistence();
        nosqlPersistence.save(cart);
    }
}
