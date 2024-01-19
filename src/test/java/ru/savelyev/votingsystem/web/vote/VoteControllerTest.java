package ru.savelyev.votingsystem.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savelyev.votingsystem.model.BaseEntity;
import ru.savelyev.votingsystem.model.Vote;
import ru.savelyev.votingsystem.repository.VoteRepository;
import ru.savelyev.votingsystem.to.VoteTo;
import ru.savelyev.votingsystem.util.JsonUtil;
import ru.savelyev.votingsystem.util.TimeLimitUtil;
import ru.savelyev.votingsystem.util.VoteUtil;
import ru.savelyev.votingsystem.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savelyev.votingsystem.web.restaurant.RestaurantTestData.MOSCOW_TIME_ID;
import static ru.savelyev.votingsystem.web.user.UserTestData.*;
import static ru.savelyev.votingsystem.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL + "/";

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        List<Vote> votes = new ArrayList<>(
                List.of(vote3, vote2, vote1));
        votes.sort(Comparator.comparing(BaseEntity::getId));
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.createVoteTos(votes)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-date")
                .param("votingDate", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.createVoteTo(vote2)));
    }

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void create() throws Exception {
        VoteTo newVote = getNewVote();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(MOSCOW_TIME_ID)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        VoteTo created = VOTE_TO_MATCHER.readFromJson(actions);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateBeforeTimeIsOver() throws Exception {
        TimeLimitUtil.setLimitTime(LocalTime.now().plus(1, ChronoUnit.SECONDS));
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(MOSCOW_TIME_ID)))
                .andDo(print())
                .andExpect(status().isOk());
        VoteTo actual = voteRepository.getUserVoteByDate(USER_ID, LocalDate.now())
                .map(VoteUtil::createVoteTo).orElse(null);
        assert actual != null;
        assertEquals(actual.getRestaurantId(), MOSCOW_TIME_ID);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterTimeIsOver() throws Exception {
        TimeLimitUtil.setLimitTime(LocalTime.now().minus(1, ChronoUnit.SECONDS));
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(MOSCOW_TIME_ID)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        VoteTo actual = voteRepository.getUserVoteByDate(USER_ID, LocalDate.now())
                .map(VoteUtil::createVoteTo).orElse(null);
        assert actual != null;
        assertEquals(actual.getRestaurantId(), vote1.getRestaurant().getId());
    }

}