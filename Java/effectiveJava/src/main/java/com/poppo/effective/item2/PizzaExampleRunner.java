package com.poppo.effective.item2;

public class PizzaExampleRunner {

    public static void main(String[] args) {

        NyPizza nyPizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addTopping(Pizza.Topping.SAUSAGE)
                .addTopping(Pizza.Topping.ONION)
                .build();
        System.out.println(nyPizza.toString());

        Calzone calzone = new Calzone.Builder()
                .addTopping(Pizza.Topping.HAM)
                .sauceInside()
                .build();
        System.out.println(calzone.toString());
    }
}
