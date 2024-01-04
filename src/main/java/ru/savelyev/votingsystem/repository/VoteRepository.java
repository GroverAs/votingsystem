package ru.savelyev.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.savelyev.votingsystem.model.Vote;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.localDate=:date")
    Optional<Vote> getUserVote(int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.localDate DESC")
    List<Vote> getAllUserVotes(int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.restaurant.id=:restaurantId ORDER BY v.localDate DESC")
    List<Vote> getAllUsersVotesForRestaurant(int userId, int restaurantId);

}
