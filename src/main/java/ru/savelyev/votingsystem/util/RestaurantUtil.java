package ru.savelyev.votingsystem.util;


import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Dish;
import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.to.NamedTo;
import ru.savelyev.votingsystem.to.RestaurantTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static ru.savelyev.votingsystem.util.DishUtil.createDishTos;

@UtilityClass
public class RestaurantUtil {
    public static RestaurantTo createRestTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), createDishTos(restaurant.getDishes()));
    }

    public static List<RestaurantTo> createRestTos(Collection<Restaurant> restaurants) {
        List<RestaurantTo> restaurantTos = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            Integer id = restaurant.getId();
            String name = restaurant.getName();
            List<Dish> dishes = restaurant.getDishes();
            restaurantTos.add(new RestaurantTo(id, name, createDishTos(dishes)));
        }
        restaurantTos.sort(Comparator.comparing(NamedTo::getName));
        return restaurantTos;
    }
}
