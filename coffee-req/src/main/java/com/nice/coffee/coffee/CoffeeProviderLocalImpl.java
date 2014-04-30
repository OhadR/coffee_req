package com.nice.coffee.coffee;

import com.nice.coffee.types.Coffee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoffeeProviderLocalImpl implements CoffeeProvider {

    private static final Map<String, Coffee> allCoffeeTypes;

    public double getCoffeePrice(String coffeeName) throws UnknownCoffeeException {
        Coffee coffee = allCoffeeTypes.get(coffeeName);
        if (coffee == null){
            throw new UnknownCoffeeException(coffeeName);
        }

        return coffee.getPrice();
    }

    static
    {
        allCoffeeTypes = new HashMap<String, Coffee>();
        allCoffeeTypes.put("Dark Chocolate", new Coffee("Dark Chocolate", 25.70));
        allCoffeeTypes.put("Caramel", new Coffee("Caramel", 25.70));
        allCoffeeTypes.put("Bukeela", new Coffee("Bukeela", 24.60));
        allCoffeeTypes.put("Ristretto", new Coffee("Ristretto", 22.40));
        allCoffeeTypes.put("Dharkan", new Coffee("Dharkan", 24.60));
        allCoffeeTypes.put("Kazzar", new Coffee("Kazzar", 24.60));
        allCoffeeTypes.put("Vanil", new Coffee("Vanil", 25.70));
        allCoffeeTypes.put("Capriccio", new Coffee("Capriccio", 22.40));
        allCoffeeTypes.put("Livanto", new Coffee("Livanto", 22.40));
        allCoffeeTypes.put("Roma", new Coffee("Roma", 22.40));
        allCoffeeTypes.put("Arpeggio", new Coffee("Arpeggio", 22.40));
        allCoffeeTypes.put("DecaffinattoIntenso", new Coffee("DecaffinattoIntenso", 22.40));
        allCoffeeTypes.put("Decaffinatto", new Coffee("Decaffinatto", 22.40));
        allCoffeeTypes.put("Cosi", new Coffee("Cosi", 22.40));
        allCoffeeTypes.put("Volluto", new Coffee("Volluto", 22.40));
        allCoffeeTypes.put("FortissioLungo", new Coffee("FortissioLungo", 23.50));
        allCoffeeTypes.put("DulsaoDoBrasil", new Coffee("DulsaoDoBrasil", 24.60));
        allCoffeeTypes.put("RosabayaDeColombia", new Coffee("RosabayaDeColombia", 24.60));
        allCoffeeTypes.put("IndriyaFromIndia", new Coffee("IndriyaFromIndia", 24.60));
        allCoffeeTypes.put("DecaffinatoLungo", new Coffee("DecaffinatoLungo", 23.50));
        allCoffeeTypes.put("VivaltoLungo", new Coffee("VivaltoLungo", 23.50));
        allCoffeeTypes.put("LinizioLungo", new Coffee("LinizioLungo", 23.50));
    }

}
