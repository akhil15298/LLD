package Decorator_design;


interface character{
    String getabilities();
}

class Mario implements character{
    @Override
    public String getabilities() {
        return "Mario has the ability to jump and run.";
    }
}

abstract class characterDecorator implements character{
    protected character decoratedCharacter;

    public characterDecorator(character decoratedCharacter) {
        this.decoratedCharacter = decoratedCharacter;
    }
}

class heightDecorator extends characterDecorator {
    public heightDecorator(character decoratedCharacter) {
        super(decoratedCharacter);
    }

    @Override
    public String getabilities() {
        return decoratedCharacter.getabilities() + " Mario can also grow taller.";
    }
}

class gunpowerdecorator extends characterDecorator {
    public gunpowerdecorator(character decoratedCharacter) {
        super(decoratedCharacter);
    }

    @Override
    public String getabilities() {
        return decoratedCharacter.getabilities() + " Mario can also shoot fireballs.";
    }
}

class starpowerdecorator extends characterDecorator {
    public starpowerdecorator(character decoratedCharacter) {
        super(decoratedCharacter);
    }

    @Override
    public String getabilities() {
        return decoratedCharacter.getabilities() + " Mario can also become invincible for a short time.";
    }
}   
public class decoratordesignpattern {
    public static void main(String[] args) {
        character mario = new Mario();
        System.out.println(mario.getabilities());

        character tallMario = new heightDecorator(mario);
        System.out.println(tallMario.getabilities());

        character fireballMario = new gunpowerdecorator(tallMario);
        System.out.println(fireballMario.getabilities());

        character invincibleMario = new starpowerdecorator(fireballMario);
        System.out.println(invincibleMario.getabilities());
    }
}
