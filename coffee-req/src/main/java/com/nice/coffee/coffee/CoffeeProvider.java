package com.nice.coffee.coffee;

import com.nice.coffee.types.Coffee;

import java.util.List;

public interface CoffeeProvider {

    public double getCoffeePrice(String coffeeName) throws UnknownCoffeeException;

}
