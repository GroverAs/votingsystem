package ru.savelyev.votingsystem.web.restaurant;

import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.to.RestaurantTo;
import ru.savelyev.votingsystem.web.MatcherFactory;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 50;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Moscow time");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Meat place");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Yapona papa");
    public static final List<Restaurant> RESTAURANTS = List.of(restaurant1, restaurant2, restaurant3);

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant1);
        updated.setName("Moscow time updated");
        return updated;
    }
}
