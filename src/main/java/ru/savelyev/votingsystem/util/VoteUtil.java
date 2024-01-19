package ru.savelyev.votingsystem.util;

import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Vote;
import ru.savelyev.votingsystem.to.VoteTo;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class VoteUtil {

    public static VoteTo createVoteTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getVotingDate(), vote.getRestaurant().getId());
    }

    public static List<VoteTo> createVoteTos(Collection<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::createVoteTo)
                .toList();
    }
}