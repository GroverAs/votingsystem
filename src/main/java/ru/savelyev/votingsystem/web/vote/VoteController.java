package ru.savelyev.votingsystem.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.savelyev.votingsystem.error.DataConflictException;
import ru.savelyev.votingsystem.error.IllegalRequestDataException;
import ru.savelyev.votingsystem.error.NotFoundException;
import ru.savelyev.votingsystem.model.Restaurant;
import ru.savelyev.votingsystem.model.User;
import ru.savelyev.votingsystem.model.Vote;
import ru.savelyev.votingsystem.repository.RestaurantRepository;
import ru.savelyev.votingsystem.repository.VoteRepository;
import ru.savelyev.votingsystem.to.VoteTo;
import ru.savelyev.votingsystem.util.VoteUtil;
import ru.savelyev.votingsystem.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.savelyev.votingsystem.util.VoteUtil.createVoteTo;
import static ru.savelyev.votingsystem.util.VoteUtil.createVoteTos;
import static ru.savelyev.votingsystem.web.AuthUser.authId;
import static ru.savelyev.votingsystem.web.AuthUser.authUser;
import static ru.savelyev.votingsystem.web.RestValidation.assureTimeOver;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    protected static final String REST_URL = "/api/profile/votes";
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/by-date")
    public VoteTo getByDate(@RequestParam LocalDate votingDate) {
        int userId = authId();
        LocalDate date = (votingDate == null) ? LocalDate.now() : votingDate;
        return voteRepository.getUserVoteByDate(userId, date).map(VoteUtil::createVoteTo)
                .orElseThrow(() -> new IllegalRequestDataException("Vote for date=" + votingDate + " not found"));
    }

    @Operation(summary = "Get all votes of logged in user")
    @GetMapping("/")
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        List<Vote> votes = voteRepository.getAllUserVotes(authId());
        return createVoteTos(votes);
    }

    @GetMapping("/restaurant")
    public List<VoteTo> getAllForRestaurant(@RequestParam int restaurantId) {
        return createVoteTos(voteRepository.getAllUsersVotesForRestaurant(restaurantId, authId()));
    }

    @Transactional
    @PostMapping("/")
    public VoteTo createVoteForRestaurant(@RequestParam int restaurantId) {
        return createVoteTo(save(authUser(), restaurantRepository.getExisted(restaurantId)));
    }

    @Transactional
    @PutMapping("/")
    public VoteTo updateVote(@RequestParam int restaurantId) {
        return createVoteTo(update(authId(), restaurantRepository.getExisted(restaurantId)));
    }

    public Vote save(User user, Restaurant restaurant) {
        Optional<Vote> vote = voteRepository.getUserVoteByDate(user.id(), LocalDate.now());
        if (vote.isPresent()) {
            throw new DataConflictException("Vote for user " + user + " already exists");
        }
        Vote newVote = new Vote(null, user, restaurant, LocalDate.now());
        if (!newVote.isNew()) {
            assureTimeOver(LocalTime.now());
        }
        return voteRepository.save(newVote);
    }

    public Vote update(int userId, Restaurant restaurant) throws NotFoundException {
        Objects.requireNonNull(restaurant);
        Optional<Vote> vote = voteRepository.getUserVoteByDate(userId, LocalDate.now());
        if (vote.isPresent()) {
            assureTimeOver(LocalTime.now());
            vote.get().setRestaurant(restaurant);
            vote.get().setVotingDate(LocalDate.now());
            return voteRepository.save(vote.get());
        } else {
            throw new NotFoundException("Current vote not found");
        }
    }

}
