package ru.didorenko.votingsystem.web.vote;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.app.AuthUser;
import ru.didorenko.votingsystem.model.Vote;
import ru.didorenko.votingsystem.service.VoteService;
import ru.didorenko.votingsystem.to.VoteTo;
import ru.didorenko.votingsystem.utill.VoteUtil;

import java.util.List;

@RestController
@RequestMapping(value = VoteUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteUserController {
    static final String REST_URL = "/api/user/votes";

    private final VoteService voteService;

    public VoteUserController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public List<VoteTo> getAllByUserId(@AuthenticationPrincipal AuthUser user) {
        return voteService.getAllByUserId(user.id());
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        Vote vote = voteService.createOrUpdateVote(restaurantId, user.id());
        return ResponseEntity.ok(VoteUtil.createTo(vote));
    }

    @GetMapping("/count-today")
    public ResponseEntity<Integer> getVoteCountForRestaurantToday(@RequestParam Integer restaurantId) {
        int voteCount = voteService.getVoteCountForRestaurantToday(restaurantId);
        return ResponseEntity.ok(voteCount);
    }
}