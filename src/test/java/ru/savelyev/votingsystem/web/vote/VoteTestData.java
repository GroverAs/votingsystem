package ru.savelyev.votingsystem.web.vote;

import ru.savelyev.votingsystem.model.Vote;
import ru.savelyev.votingsystem.to.VoteTo;
import ru.savelyev.votingsystem.web.MatcherFactory;
import ru.savelyev.votingsystem.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.util.List;

import static ru.savelyev.votingsystem.web.user.UserTestData.admin;
import static ru.savelyev.votingsystem.web.user.UserTestData.user;

public class VoteTestData {
    public static MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int VOTE1_ID = 1;
    public static final int VOTE2_ID = 2;
    public static final int VOTE3_ID = 3;
    public static final int VOTE4_ID = 4;

    public static final Vote vote1 = new Vote(VOTE1_ID, user, RestaurantTestData.moscow_time, LocalDate.now());
    public static final Vote vote2 = new Vote(VOTE2_ID, user, RestaurantTestData.moscow_time, LocalDate.now().minusDays(1));
    public static final Vote vote3 = new Vote(VOTE3_ID, user, RestaurantTestData.yapona_papa,LocalDate.now().minusDays(2));
    public static final Vote vote5 = new Vote(VOTE4_ID, admin, RestaurantTestData.meat_place, LocalDate.now());
    public static final List<Vote> user_votes = List.of(vote3, vote2, vote1);


}
