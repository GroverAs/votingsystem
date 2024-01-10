package ru.savelyev.votingsystem.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.savelyev.votingsystem.model.Dish;
import ru.savelyev.votingsystem.repository.DishRepository;
import ru.savelyev.votingsystem.repository.RestaurantRepository;
import ru.savelyev.votingsystem.to.DishTo;
import ru.savelyev.votingsystem.util.DishUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.savelyev.votingsystem.web.RestValidation.*;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id,
                    @PathVariable int restaurantId) {
        return dishRepository.isDishRelateToRestaurant(id, restaurantId);
    }

    @GetMapping
    public List<Dish> getAllByRestaurant(@PathVariable int restaurantId) {
        return dishRepository.getAllDishesByRestaurantId(restaurantId);
    }

    @GetMapping("/by-date")
    public List<DishTo> getAllByRestaurantAndDate(@PathVariable int restaurantId,
                                                  @RequestParam LocalDate date) {
        List<Dish> getAllDishesByDate = dishRepository.getAllByDate(restaurantId, date == null ? LocalDate.now() : date);
        return DishUtil.getDishTos(getAllDishesByDate);
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody DishTo dishTo,
                                                   @PathVariable int restaurantId) {
        checkNew(dishTo);
        Dish dish = DishUtil.getDish(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        dish.setCreatingDate(LocalDate.now());
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Transactional
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo,
                       @PathVariable int id,
                       @PathVariable int restaurantId) {
        log.info("update dish {} for restaurant {}", id, restaurantId);
        assureIdConsistent(dishTo, id);
        Dish dish = DishUtil.getDish(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        dish.setCreatingDate(LocalDate.now());
        dishRepository.save(dish);
    }

    @Transactional
    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int dishId) {
        dishRepository.delete(dishId);

    }
}
