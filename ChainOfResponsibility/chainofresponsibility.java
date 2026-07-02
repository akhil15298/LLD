package ChainOfResponsibility;

import java.util.Scanner;

abstract class MoneyHandler{
    protected MoneyHandler nextHandler;

    public MoneyHandler() {
        this.nextHandler = null;
    }

    public void setNextHandler(MoneyHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void dispense(int amount);
}

class thousandHandler extends MoneyHandler {
    private int numnotes;

    public thousandHandler(int numnotes) {
        this.numnotes = numnotes;
    }

    @Override
    public void dispense(int amount) {
        int notesneeded = amount / 1000;
        if(notesneeded>numnotes){
            notesneeded = numnotes;
            numnotes = 0;
        }else{
            numnotes -= notesneeded;
        }
        if(notesneeded>0){
            System.out.println("Dispensing " + notesneeded + " 1000 notes");
        }
        int remainingAmount = amount - (notesneeded * 1000);
        if(remainingAmount > 0 && nextHandler != null){
            nextHandler.dispense(remainingAmount);
        } else if (remainingAmount > 0) {
            System.out.println("Cannot dispense the remaining amount: " + remainingAmount);
        }
    }
}

class fivehundredHandler extends MoneyHandler {
    private int numnotes;

    public fivehundredHandler(int numnotes) {
        this.numnotes = numnotes;
    }

    @Override
    public void dispense(int amount) {
        int notesneeded = amount / 500;
        if(notesneeded>numnotes){
            notesneeded = numnotes;
            numnotes = 0;
        }else{
            numnotes -= notesneeded;
        }
        if(notesneeded>0){
            System.out.println("Dispensing " + notesneeded + " 500 notes");
        }
        int remainingAmount = amount - (notesneeded * 500);
        if(remainingAmount > 0 && nextHandler != null){
            nextHandler.dispense(remainingAmount);
        } else if (remainingAmount > 0) {
            System.out.println("Cannot dispense the remaining amount: " + remainingAmount);
        }
    }
}

class twohundredHandler extends MoneyHandler {
    private int numnotes;

    public twohundredHandler(int numnotes) {
        this.numnotes = numnotes;
    }

    @Override
    public void dispense(int amount) {
        int notesneeded = amount / 200;
        if(notesneeded>numnotes){
            notesneeded = numnotes;
            numnotes = 0;
        }else{
            numnotes -= notesneeded;
        }
        if(notesneeded>0){
            System.out.println("Dispensing " + notesneeded + " 200 notes");
        }
        int remainingAmount = amount - (notesneeded * 200);
        if(remainingAmount > 0 && nextHandler != null){
            nextHandler.dispense(remainingAmount);
        } else if (remainingAmount > 0) {
            System.out.println("Cannot dispense the remaining amount: " + remainingAmount);
        }
    }
}

class onehundredHandler extends MoneyHandler {
    private int numnotes;

    public onehundredHandler(int numnotes) {
        this.numnotes = numnotes;
    }

    @Override
    public void dispense(int amount) {
        int notesneeded = amount / 100;
        if(notesneeded>numnotes){
            notesneeded = numnotes;
            numnotes = 0;
        }else{
            numnotes -= notesneeded;
        }
        if(notesneeded>0){
            System.out.println("Dispensing " + notesneeded + " 100 notes");
        }
        int remainingAmount = amount - (notesneeded * 100);
        if(remainingAmount > 0 && nextHandler != null){
            nextHandler.dispense(remainingAmount);
        } else if (remainingAmount > 0) {
            System.out.println("Cannot dispense the remaining amount: " + remainingAmount);
        }
    }
}


public class chainofresponsibility {
    
    public static void main(String[] args) {
        MoneyHandler thousandHandler = new thousandHandler(5);
        MoneyHandler fivehundredHandler = new fivehundredHandler(10);   
        MoneyHandler twohundredHandler = new twohundredHandler(20);
        MoneyHandler onehundredHandler = new onehundredHandler(50);

        thousandHandler.setNextHandler(fivehundredHandler);
        fivehundredHandler.setNextHandler(twohundredHandler);   
        twohundredHandler.setNextHandler(onehundredHandler);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to dispense: ");
        int amountToDispense = scanner.nextInt();

        System.out.println("Requesting to dispense: " + amountToDispense);
        thousandHandler.dispense(amountToDispense);
    }
}
