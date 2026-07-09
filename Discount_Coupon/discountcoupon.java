package Discount_Coupon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


interface discountstrategy {
    double calculate(double amount);
}

class flatdiscountstrategy implements discountstrategy{

    private double amount;
    public flatdiscountstrategy(double amount){
        this.amount = amount;
    }

    @Override
    public double calculate(double baseamount){
        return Math.min(baseamount, amount);
    }
}

class percentagediscountstrategy implements discountstrategy{

    private double percentage;
    public percentagediscountstrategy(double percentage){
        this.percentage = percentage;
    }

    @Override
    public double calculate(double baseamount){
        return baseamount * (percentage / 100);
    }
}

class percentagewithcapdiscountstrategy implements discountstrategy{

    private double percentage;
    private double cap;
    public percentagewithcapdiscountstrategy(double percentage, double cap){
        this.percentage = percentage;
        this.cap = cap;
    }

    @Override
    public double calculate(double baseamount){
        double discount = baseamount * (percentage / 100);
        return Math.min(discount, cap);
    }
}


enum strategytype {
    FLAT,
    PERCENTAGE,
    PERCENTAGE_WITH_CAP
}

class discountstrategymanager{
    private static discountstrategymanager instance;

    static synchronized discountstrategymanager getInstance(){
        if(instance == null){
            instance = new discountstrategymanager();
        }
        return instance;
    }

    public discountstrategy getstrategy(strategytype type, double... params){  // instead of passing double[] we can use varargs like double... params
        switch(type){
            case FLAT:
                return new flatdiscountstrategy(params[0]);
            case PERCENTAGE:
                return new percentagediscountstrategy(params[0]);
            case PERCENTAGE_WITH_CAP:
                return new percentagewithcapdiscountstrategy(params[0], params[1]);
            default:
                throw new IllegalArgumentException("Invalid strategy type");
        }
    }
}

class product{
    private String name;
    private String category;
    private double price;


    public product(String name, String category, double price){
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}

class cartitem{
    private product product;
    private int quantity;

    public cartitem(product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public double itemtotal(){
        return product.getPrice() * quantity;
    }

    public product getProduct() {
        return product;
    }
}

class cart{
    private List<cartitem> items = new ArrayList<>();
    private double originaltotal = 0.0;
    private double currenttotal = 0.0;
    private boolean loyalitymember;
    private String paymentbank;

    public cart(){
        
        this.loyalitymember = false;
        this.paymentbank = null;
    }

    public void additem(product product, int quantity){
        cartitem item = new cartitem(product, quantity);
        items.add(item);
        originaltotal += item.itemtotal();
        currenttotal += item.itemtotal();
    }

    public double getOriginalTotal() {
        return originaltotal;
    }

    public double getCurrentTotal() {
        return currenttotal;
    }

    public void applydiscount(double amount){
        currenttotal -= amount;
        if(currenttotal < 0){
            currenttotal = 0;
        }
    }

    public void setLoyalityMember(boolean loyalitymember) {
        this.loyalitymember = loyalitymember;
    }

    public void setPaymentBank(String paymentbank) {
        this.paymentbank = paymentbank;
    }

    public boolean isLoyalityMember() {
        return loyalitymember;
    }

    public String getPaymentBank() {
        return paymentbank;
    }

    public List<cartitem> getItems() {
        return items;
    }
}

abstract class coupon{
    private coupon next;

    public coupon(){      
        this.next = null;
    }

    public void setNext(coupon next){
        this.next = next;
    }

    public coupon getNext(){
        return next;
    }

    public void applydiscount(cart cart){
        if(isapplicable(cart)){
            double discountamount = getdiscount(cart);
            cart.applydiscount(discountamount);
            System.out.println(name() + " applied. Discount amount: " + discountamount);
            if(!iscombinable()){
                return;
            }
        }
        if(next != null){
            next.applydiscount(cart);
        }
    }
    public abstract boolean isapplicable(cart cart);
    public abstract double getdiscount(cart cart);
    public abstract String name();
    public boolean iscombinable(){
        return true;
    }
}


class seasonaloffer extends coupon{
   private discountstrategy strategy;
   private double percentage;
   private String category;

   public seasonaloffer(double percentage, String category){
        this.percentage = percentage;
        this.category = category;
        this.strategy = discountstrategymanager.getInstance().getstrategy(strategytype.PERCENTAGE, percentage);
    }

    @Override
    public boolean isapplicable(cart cart){
        for(cartitem item : cart.getItems()){
            if(item.getProduct().getCategory().equalsIgnoreCase(category)){
                return true;
            }
        }
        return false;
    }

    @Override
    public double getdiscount(cart cart){
        double total = 0.0;
        for(cartitem item : cart.getItems()){
            if(item.getProduct().getCategory().equalsIgnoreCase(category)){
                total += item.itemtotal();
            }
        }
        return strategy.calculate(total);
    }

    @Override
    public String name(){
        return "Seasonal Offer"  + " (" + percentage + "% off on " + category + ")";
    }

}

class loyalitydiscount extends coupon{
    private discountstrategy strategy;
    private double percentage;

    public loyalitydiscount(double percentage){
        this.percentage = percentage;
        this.strategy = discountstrategymanager.getInstance().getstrategy(strategytype.PERCENTAGE, percentage);
    }

    @Override
    public boolean isapplicable(cart cart){
        return cart.isLoyalityMember();
    }

    @Override
    public double getdiscount(cart cart){
        return strategy.calculate(cart.getCurrentTotal());
    }

    @Override
    public String name(){
        return "Loyality Discount" + " (" + percentage + "% off for loyality members)";
    }
}

class bulkpurchasediscount extends coupon{
    private double threshold;
    private double flatdiscount;
    private discountstrategy strategy;

    public bulkpurchasediscount(double threshold, double flatdiscount){
        this.threshold = threshold;
        this.flatdiscount = flatdiscount;
        this.strategy = discountstrategymanager.getInstance().getstrategy(strategytype.FLAT, flatdiscount);
    }

    @Override
    public boolean isapplicable(cart cart){
        return cart.getOriginalTotal() >= threshold;
    }

    @Override
    public double getdiscount(cart cart){
        return strategy.calculate(cart.getCurrentTotal());
    }

    @Override
    public String name(){
        return "Bulk Purchase Discount" + " (Flat " + flatdiscount + " off for purchases above " + threshold + ")";
    }
}

class bankdiscount extends coupon{
   private String bankname;
   private discountstrategy strategy;
   private double percentage;
   private double cap;
   private double minspend;

    public bankdiscount(String bankname, double percentage, double cap, double minspend){
          this.bankname = bankname;
          this.percentage = percentage;
          this.cap = cap;
          this.minspend = minspend;
          this.strategy = discountstrategymanager.getInstance().getstrategy(strategytype.PERCENTAGE_WITH_CAP, percentage, cap);
     }
    
     @Override
     public boolean isapplicable(cart cart){
          return cart.getPaymentBank() != null && cart.getPaymentBank().equalsIgnoreCase(bankname) && cart.getCurrentTotal() >= minspend;
     }
    
     @Override
     public double getdiscount(cart cart){
          return strategy.calculate(cart.getCurrentTotal());
     }
    
     @Override
     public String name(){
          return "Bank Discount" + " (" + percentage + "% off with " + bankname + ", capped at " + cap + " for minimum spend of " + minspend + ")";
     }
}


class couponmanager{
    private static couponmanager instance;
    private coupon head;
    private final ReentrantLock lock = new ReentrantLock();


    private couponmanager(){
        head = null;
    }

    public static synchronized couponmanager getInstance(){
        if(instance == null){
            instance = new couponmanager();
        }
        return instance;
    }

    public void registercoupon(coupon newcoupon){
        lock.lock();
        try{
            if(head == null){
                head = newcoupon;
            } else {
                coupon current = head;
                while(current.getNext() != null){
                    current = current.getNext();
                }
                current.setNext(newcoupon);
            }
        } finally {
            lock.unlock();
        }
    }

    public  List<String> getapplicable(cart cart){
        lock.lock();
        try{
            
            List<String> applicableCoupons = new ArrayList<>();
            coupon current = head;
            while(current != null){
                if(current.isapplicable(cart)){
                    applicableCoupons.add(current.name());
                }
                current = current.getNext();
            }
            return applicableCoupons;
        } finally {
            lock.unlock();
        }

    }

    public double applyall(cart cart){
        lock.lock();
        try{
            if(head != null){
                head.applydiscount(cart);
            }
            return cart.getCurrentTotal();
        } finally {
            lock.unlock();
        }
    }

}


public class discountcoupon {
    public static void main(String[] args) {
        // Create products
        product laptop = new product("Laptop", "Electronics", 1000);
        product phone = new product("Phone", "Electronics", 500);
        product tshirt = new product("T-Shirt", "Clothing", 50);

        // Create a cart and add items
        cart myCart = new cart();
        myCart.additem(laptop, 1);
        myCart.additem(phone, 2);
        myCart.additem(tshirt, 3);

        // Set loyalty member and payment bank
        myCart.setLoyalityMember(true);
        myCart.setPaymentBank("BankA");

        // Register coupons
        couponmanager couponManager = couponmanager.getInstance();
        couponManager.registercoupon(new seasonaloffer(10, "Electronics"));
        couponManager.registercoupon(new loyalitydiscount(5));
        couponManager.registercoupon(new bulkpurchasediscount(1500, 100));
        couponManager.registercoupon(new bankdiscount("BankA", 15, 200, 1000));

        // Get applicable coupons
        List<String> applicableCoupons = couponManager.getapplicable(myCart);
        System.out.println("Applicable Coupons: " + applicableCoupons);
        System.out.println("============================");

        // Apply all coupons and get final total
        double finalTotal = couponManager.applyall(myCart);
        System.out.println("Final Total after applying coupons: " + finalTotal);
    }
}
