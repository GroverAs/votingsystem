package ru.savelyev.votingsystem.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savelyev.votingsystem.model.NamedEntity;
import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.repository.RestaurantRepository;
import ru.savelyev.votingsystem.web.AbstractControllerTest;
import ru.savelyev.votingsystem.web.user.UserTestData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savelyev.votingsystem.web.dish.DishTestData.*;
import static ru.savelyev.votingsystem.web.restaurant.RestaurantTestData.*;

@WithUserDetails(value = UserTestData.USER_MAIL)
class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + "/";

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    void getAll() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>(
                List.of(moscow_time, meat_place, yapona_papa));
        restaurants.sort(Comparator.comparing(NamedEntity::getName));
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurants));
    }

    @Test
    void getByIdAndDateWithDishes() throws Exception {
        moscow_time.setDishes(moscowTime_menu);
        perform(MockMvcRequestBuilders.get(REST_URL + MOSCOW_TIME_ID + "/with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(moscow_time));
    }

    @Test
    void getAllWithDishesByDate() throws Exception {
        moscow_time.setDishes(moscowTime_menu);
        meat_place.setDishes(meatPlace_menu);
        yapona_papa.setDishes(yaponaPapa_menu);
        perform(MockMvcRequestBuilders.get(REST_URL + "with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANTS));
    }
}