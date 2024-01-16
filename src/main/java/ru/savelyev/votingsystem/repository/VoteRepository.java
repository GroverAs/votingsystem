package ru.savelyev.votingsystem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.savelyev.votingsystem.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.votingDate=:votingDate")
    Optional<Vote> getUserVoteByDate(int userId, LocalDate votingDate);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.votingDate DESC")
    List<Vote> getAllUserVotes(int userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.restaurant.id=:restaurantId ORDER BY v.votingDate DESC")
    List<Vote> getAllUsersVotesForRestaurant(int userId, int restaurantId);

}
