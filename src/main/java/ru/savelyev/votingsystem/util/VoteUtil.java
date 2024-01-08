package ru.savelyev.votingsystem.util;

import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.model.Vote;
import ru.savelyev.votingsystem.to.VoteTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@UtilityClass
public class VoteUtil {

    public static VoteTo createVoteTo(Vote vote) {
        return new VoteTo(vote.getVotingDate(), vote.getRestaurant().id());
    }

    public static List<VoteTo> createVoteTos(Collection<Vote> votes) {
        List<VoteTo> voteTos = new ArrayList<>();
        for (Vote vote : votes) {
            voteTos.add(new VoteTo(vote.getVotingDate(), vote.getRestaurant().id()));
        }
        voteTos.sort(Comparator.comparing(VoteTo::getVotingDate).reversed());
        return voteTos;
    }
}
