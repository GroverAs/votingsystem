package ru.savelyev.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.savelyev.votingsystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.creatingDate=:date AND r.id=:id")
    Optional<Restaurant> getByIdAndLocalDate(int id, LocalDate date);

    @Query("SELECT DISTINCT r from Restaurant r JOIN FETCH r.dishes d WHERE d.creatingDate=:date")
    List<Restaurant> getAllWithDishesByLocalDate(LocalDate date);
}