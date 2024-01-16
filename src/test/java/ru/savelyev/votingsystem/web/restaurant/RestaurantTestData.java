package ru.savelyev.votingsystem.web.restaurant;

import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.to.RestaurantTo;
import ru.savelyev.votingsystem.web.MatcherFactory;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final int MOSCOW_TIME_ID = 1;
    public static final int MEAT_PLACE_ID = 2;
    public static final int YAPONA_PAPA_ID = 3;
    public static final int NOT_FOUND = 500;

    public static final Restaurant moscow_time = new Restaurant(MOSCOW_TIME_ID, "Moscow time");
    public static final Restaurant meat_place = new Restaurant(MEAT_PLACE_ID, "Meat place");
    public static final Restaurant yapona_papa = new Restaurant(YAPONA_PAPA_ID, "Yapona papa");
    public static final List<Restaurant> RESTAURANTS = List.of(moscow_time, meat_place, yapona_papa);

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(moscow_time);
        updated.setName("Moscow time updated");
        return updated;
    }
}
