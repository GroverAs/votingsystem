package ru.savelyev.votingsystem.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savelyev.votingsystem.model.Dish;
import ru.savelyev.votingsystem.repository.DishRepository;
import ru.savelyev.votingsystem.util.DishUtil;
import ru.savelyev.votingsystem.util.JsonUtil;
import ru.savelyev.votingsystem.web.AbstractControllerTest;
import ru.savelyev.votingsystem.web.user.UserTestData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savelyev.votingsystem.web.dish.DishTestData.*;
import static ru.savelyev.votingsystem.web.restaurant.RestaurantTestData.MOSCOW_TIME_ID;
import static ru.savelyev.votingsystem.web.restaurant.RestaurantTestData.YAPONA_PAPA_ID;

@WithUserDetails(value = UserTestData.ADMIN_MAIL)
class DishControllerTest extends AbstractControllerTest {

    static final String REST_URL = DishController.REST_URL + '/';

    @Autowired
    private DishRepository dishRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 1, MOSCOW_TIME_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(moscowTime_Dish1));
    }

    @Test
    void getAllByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, YAPONA_PAPA_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(DishUtil.getDishTos(yaponaPapa_menu_allDays)));
    }

    @Test
    void getAllByRestaurantAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by-date", MOSCOW_TIME_ID)
                .param("creatingDate", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(DishUtil.getDishTos(moscowTime_menu)));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 1, MOSCOW_TIME_ID))
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.get(1, MOSCOW_TIME_ID).isPresent());
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders
                .put(REST_URL + 1, MOSCOW_TIME_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(dishRepository.get(1, MOSCOW_TIME_ID).orElse(null), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = DishTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());
        Dish created = DishTestData.getNew();
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
//        DISH_MATCHER.assertMatch(dishRepository.get(newId, MOSCOW_TIME_ID).orElse(null), newDish);
        DISH_MATCHER.assertMatch(dishRepository.getExisted(newId), (newDish));

//        MenuTo newMenu = getNew();
//        create(newMenu, status().isCreated());
//
//        newMenu.setId(MENU_NEW_ID);
//        MENU_MATCHER.assertMatch(repository.getExisted(MENU_NEW_ID), createFromTo(newMenu));
    }

    @Test
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, 9000);
        perform(MockMvcRequestBuilders
                .post(REST_URL, MOSCOW_TIME_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}