package ru.savelyev.votingsystem.util;


import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.to.RestaurantTo;

import java.util.Collection;
import java.util.List;

import static ru.savelyev.votingsystem.util.DishUtil.createDishTos;

@UtilityClass
public class RestaurantUtil {
    public static RestaurantTo createRestTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), createDishTos(restaurant.getDishes()));
    }

    public static List<RestaurantTo> createRestTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantUtil::createRestTo)
                .toList();
    }
}
