package com.nice.coffee.coffee;

import com.nice.coffee.types.Coffee;

import java.util.List;

public interface CoffeeProvider {

    public float getCoffeePrice(String coffeeName);

    public List<Coffee> getAllCoffeeList();
}
