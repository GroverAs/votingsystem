package ru.savelyev.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.savelyev.votingsystem.error.IllegalRequestDataException;
import ru.savelyev.votingsystem.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.id=?1 AND d.restaurant.id=?2")
    Optional<Dish> get(int id, int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.creatingDate DESC")
    List<Dish> getAll(int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.creatingDate=:creatingDate")
    List<Dish> getAllByDate(int restaurantId, LocalDate creatingDate);
}
