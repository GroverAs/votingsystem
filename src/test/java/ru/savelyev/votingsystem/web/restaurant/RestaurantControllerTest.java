package ru.savelyev.votingsystem.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.web.AbstractControllerTest;
import ru.savelyev.votingsystem.web.user.UserTestData;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savelyev.votingsystem.web.dish.DishTestData.*;
import static ru.savelyev.votingsystem.web.restaurant.RestaurantTestData.*;

@WithUserDetails(value = UserTestData.USER_MAIL)
class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + "/";

    @Test
    void getAll() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>(
                List.of(restaurant3, restaurant2, restaurant1));
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson((restaurants)));
    }

    @Test
    void getByIdAndDateWithDishes() throws Exception {
        restaurant1.setDishes(moscowTime_menu);
        perform(MockMvcRequestBuilders.get(REST_URL + MOSCOW_TIME_ID + "/with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1));
    }

//    @Test
//    void getAllWithDishesByDate() throws Exception {
//        restaurant1.setDishes(moscowTime_menu);
//        restaurant2.setDishes(meatPlace_menu);
//        restaurant3.setDishes(yaponaPapa_menu);
//        perform(MockMvcRequestBuilders.get(REST_URL + "with-menu"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(RESTAURANT_MATCHER.contentJson(RestaurantUtil.createRestTos(RESTAURANTS)));
//    }
}