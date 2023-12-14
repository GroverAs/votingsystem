package ru.savelyev.votingsystem.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.savelyev.votingsystem.model.Restaurant;

@Transactional
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
