package ru.savelyev.votingsystem.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.savelyev.votingsystem.util.VoteUtil.createVoteTos;
import static ru.savelyev.votingsystem.web.AuthUser.authId;
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
    public VoteTo getByDate(int userId, LocalDate date) {
        LocalDate votingDate = (date == null) ? LocalDate.now() : date;
        return voteRepository.getUserVoteByDate(userId, votingDate).map(VoteUtil::createVoteTo)
                .orElseThrow(() -> new IllegalRequestDataException("Vote for date=" + votingDate + " not found"));
    }

    @GetMapping
    public List<VoteTo> getAll(AuthUser authUser) {
        List<Vote> votes = voteRepository.getAllUserVotes(authUser.id());
        return createVoteTos(votes);
    }

    @GetMapping("/restaurant")
    public List<VoteTo> getAllForRestaurant(@RequestParam int restaurantId) {
        return createVoteTos(voteRepository.getAllUsersVotesForRestaurant(restaurantId, authId()));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<VoteTo> createVoteForRestaurant(Restaurant restaurant, User user) {
        Optional<Vote> vote = voteRepository.getUserVoteByDate(user.id(), LocalDate.now());
        if(vote.isPresent()) {
            throw new DataConflictException("Vote for user " + user + " already created.");
        }
        LocalDate voteDate = LocalDate.now();
        Vote newVote = new Vote(null, user, restaurant, voteDate);
        if (!newVote.isNew()) {
            assureTimeOver(LocalTime.now());
        }
        voteRepository.save(newVote);
        VoteTo voteTo = VoteUtil.createVoteTo(newVote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(voteTo.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(voteTo);
    }

    @PutMapping
    public VoteTo updateVote(User user, int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
        LocalDate voteDate = LocalDate.now();
        Vote vote = voteRepository.getUserVoteByDate(user.id(), voteDate)
                .orElse(new Vote(voteDate, user, restaurant));
        if (!vote.isNew()) {
            assureTimeOver(LocalTime.now());
        }
        Vote created = voteRepository.save(vote);
        created.setRestaurant(restaurant);
        return VoteUtil.createVoteTo(created);
    }

}
