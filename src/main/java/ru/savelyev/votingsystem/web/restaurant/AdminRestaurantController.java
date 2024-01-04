package ru.savelyev.votingsystem.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.savelyev.votingsystem.error.IllegalRequestDataException;
import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.repository.RestaurantRepository;
import ru.savelyev.votingsystem.web.RestValidation;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class AdminRestaurantController {
    protected static final String REST_URL = "/api/admin/restaurants";
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{restaurantId}")
    public Restaurant get(@PathVariable int restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new IllegalRequestDataException("Restaurant with id=" + restaurantId + " not found"));
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        RestValidation.checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantRepository.deleteExisted(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        restaurantRepository.findById(id).orElseThrow(
                () -> new IllegalRequestDataException("Restaurant with id=" + id + " not found"));
        RestValidation.assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }
}
