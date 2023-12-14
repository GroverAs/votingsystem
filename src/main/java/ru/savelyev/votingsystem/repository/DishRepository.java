package ru.savelyev.votingsystem.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.savelyev.votingsystem.model.Dish;

@Transactional
public interface DishRepository extends BaseRepository<Dish> {
}
