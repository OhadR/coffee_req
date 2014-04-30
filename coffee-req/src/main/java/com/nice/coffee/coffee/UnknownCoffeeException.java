package com.nice.coffee.coffee;

import java.text.MessageFormat;

/**
 * Created with IntelliJ IDEA.
 * User: zachb
 * Date: 30/04/14
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
public class UnknownCoffeeException extends Exception {
    private String coffeeName;

    public UnknownCoffeeException(String coffeeName) {
        super(MessageFormat.format("The coffee name \"{0}\" isn unknown.", coffeeName));
        this.coffeeName = coffeeName;
    }


}
