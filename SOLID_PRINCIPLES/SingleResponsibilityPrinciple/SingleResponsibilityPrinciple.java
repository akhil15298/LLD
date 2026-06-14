package SOLID_PRINCIPLES.SingleResponsibilityPrinciple;

import java.util.ArrayList;
import java.util.List;

class product{
    String name;
    double price;
    public product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class shoppingcart {
    private List<product> products;

    public shoppingcart() {
        this.products = new ArrayList<>();
    }

    public void addproduct(product product) {
        products.add(product);
        System.out.println(product.name + " added to cart.");
    }

    public List<product> getproducts() {
        return products;
    }

    public double calculateTotal() {
        double total = 0;
        for (product product : products) {
            total += product.price;
        }
        return total;
    }
}

class shoppingcartprinter {

    private shoppingcart cart;
    public void printreceipt(shoppingcart cart) {
        System.out.println("Receipt:");
        for (product product : cart.getproducts()) {
            System.out.println(product.name + ": $" + product.price);
        }
        System.out.println("Total: $" + cart.calculateTotal());
    }
}   

interface percistence {
    void save(shoppingcart cart);
}

class mysqlpercistence implements percistence {
    @Override
    public void save(shoppingcart cart) {
        // Code to save shopping cart to MySQL database
        System.out.println("Shopping cart saved to MySQL database.");
    }
}

class nosqlpercistence implements percistence {
    @Override
    public void save(shoppingcart cart) {
        // Code to save shopping cart to NoSQL database
        System.out.println("Shopping cart saved to NoSQL database.");
    }
}

class filepercistence implements percistence {
    @Override
    public void save(shoppingcart cart) {
        // Code to save shopping cart to a file
        System.out.println("Shopping cart saved to a file.");
    }
}


public class SingleResponsibilityPrinciple {
    public static void main(String[] args) {
        shoppingcart cart = new shoppingcart();
        cart.addproduct(new product("Laptop", 999.99));
        cart.addproduct(new product("Headphones", 199.99));

        shoppingcartprinter printer = new shoppingcartprinter();
        printer.printreceipt(cart);

        percistence mysqlPersistence = new mysqlpercistence();
        mysqlPersistence.save(cart);

        percistence noSqlPersistence = new nosqlpercistence();
        noSqlPersistence.save(cart);

        percistence filePersistence = new filepercistence();
        filePersistence.save(cart);
    }
}
