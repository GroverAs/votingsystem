package ru.savelyev.votingsystem.web.dish;

import io.swagger.v3.oas.annotations.Operation;
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

import static ru.savelyev.votingsystem.util.DishUtil.getDish;
import static ru.savelyev.votingsystem.web.RestValidation.assureIdConsistent;
import static ru.savelyev.votingsystem.web.RestValidation.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Operation(summary = "Get dish of restaurant by id")
    @GetMapping("/{id}")
    public Dish get(@PathVariable int id,
                    @PathVariable int restaurantId) {
        return dishRepository.isDishRelateToRestaurant(id, restaurantId);
    }

    @Operation(summary = "Get all dishes of the restaurant")
    @GetMapping("/")
    public List<DishTo> getAllByRestaurant(@PathVariable int restaurantId) {
        List<Dish> allDishes = dishRepository.getAllDishesByRestaurantId(restaurantId);
        return DishUtil.getDishTos(allDishes);
    }

    @Operation(summary = "Get all dishes of the restaurant by date")
    @GetMapping("/by-date")
    public List<DishTo> getAllByRestaurantAndDate(@PathVariable int restaurantId, @RequestParam LocalDate creatingDate) {
        List<Dish> getAllDishesByDate = dishRepository.getAllByDate(restaurantId, creatingDate);
        return DishUtil.getDishTos(getAllDishesByDate);
    }

    @Operation(summary = "Create dish of the restaurant")
    @Transactional
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @Operation(summary = "Update dish of the restaurant")
    @Transactional
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo,
                       @PathVariable int id,
                       @PathVariable int restaurantId) {
        log.info("update dish {} for restaurant {}", id, restaurantId);
        assureIdConsistent(dishTo, id);
        Dish dish = getDish(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        dish.setCreatingDate(LocalDate.now());
        dishRepository.save(dish);
    }

    @Operation(summary = "Delete dish of the restaurant")
    @Transactional
    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int dishId) {
        dishRepository.delete(dishId);

    }
}
