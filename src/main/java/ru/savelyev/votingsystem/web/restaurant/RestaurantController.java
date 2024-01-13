package ru.savelyev.votingsystem.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savelyev.votingsystem.error.NotFoundException;
import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.repository.RestaurantRepository;
import ru.savelyev.votingsystem.to.RestaurantTo;
import ru.savelyev.votingsystem.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    public static final String REST_URL = "/api/restaurants";
    private final RestaurantRepository repository;

    @GetMapping("/")
    public List<Restaurant> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @GetMapping("/with-menu")
    public List<RestaurantTo> getAllWithDishesByDate() {
        List<Restaurant> allByDateWithDishes = repository.getAllWithDishesByLocalDate(LocalDate.now());
        return RestaurantUtil.createRestTos(allByDateWithDishes);
    }

    @GetMapping("/{id}/with-menu")
    public RestaurantTo getByIdAndDateWithDishes(@PathVariable int id) {
        return repository.getByIdAndLocalDate(id, LocalDate.now())
                .map(RestaurantUtil::createRestTo)
                .orElseThrow(() -> new NotFoundException("Restaurant with id=" + id + " not found"));
    }
}
