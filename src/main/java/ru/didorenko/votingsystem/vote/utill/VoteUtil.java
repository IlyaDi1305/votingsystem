package ru.didorenko.votingsystem.vote.utill;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.user.model.User;
import ru.didorenko.votingsystem.vote.model.Vote;
import ru.didorenko.votingsystem.vote.to.VoteTo;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class VoteUtil {

    public static VoteTo crateTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getDate(), vote.getRestaurant().getId());
    }

    public static List<VoteTo> createToList(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::crateTo)
                .collect(Collectors.toList());
    }
}
