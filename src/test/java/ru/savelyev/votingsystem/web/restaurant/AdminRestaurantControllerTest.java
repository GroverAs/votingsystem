package ru.savelyev.votingsystem.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.repository.RestaurantRepository;
import ru.savelyev.votingsystem.util.JsonUtil;
import ru.savelyev.votingsystem.web.AbstractControllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savelyev.votingsystem.util.RestaurantUtil.createRestTo;
import static ru.savelyev.votingsystem.util.RestaurantUtil.createRestTos;
import static ru.savelyev.votingsystem.web.restaurant.RestaurantTestData.*;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + YAPONA_PAPA_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(createRestTo(yapona_papa)));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(createRestTos(RESTAURANTS)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Restaurant with id=" + NOT_FOUND + " not found")));
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getById(newId), newRestaurant);
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, null);
        perform(MockMvcRequestBuilders
                .post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders
                .put(REST_URL + MOSCOW_TIME_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getById(MOSCOW_TIME_ID), getUpdated());
    }

    @Test
    void deleteWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAT_PLACE_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(restaurantRepository.findById(MEAT_PLACE_ID).isPresent());
    }

@Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}