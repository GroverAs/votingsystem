package ru.savelyev.votingsystem.util;


import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.to.RestaurantTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@UtilityClass
public class RestaurantUtil {

    public static RestaurantTo createTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }

    public static List<RestaurantTo> createTos(Collection<Restaurant> restaurants) {
        List<RestaurantTo> list = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            RestaurantTo restaurantTo = createTo(restaurant);
            list.add(restaurantTo);
        }
        return list;
    }
}
