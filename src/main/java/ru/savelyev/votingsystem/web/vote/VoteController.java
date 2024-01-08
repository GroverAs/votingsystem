package ru.savelyev.votingsystem.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savelyev.votingsystem.error.IllegalRequestDataException;
import ru.savelyev.votingsystem.repository.RestaurantRepository;
import ru.savelyev.votingsystem.repository.VoteRepository;
import ru.savelyev.votingsystem.to.VoteTo;
import ru.savelyev.votingsystem.util.VoteUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.savelyev.votingsystem.util.VoteUtil.createVoteTos;
import static ru.savelyev.votingsystem.web.AuthUser.authId;

@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@RestController
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
    public List<VoteTo> getAll() {
        return createVoteTos(voteRepository.getAllUserVotes(authId()));
    }
}