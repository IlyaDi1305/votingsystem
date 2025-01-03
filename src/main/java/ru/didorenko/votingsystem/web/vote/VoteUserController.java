package ru.didorenko.votingsystem.web.vote;

import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VoteTo> createVote(@RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        Vote vote = voteService.createVote(restaurantId, user.id());
        return ResponseEntity.ok(VoteUtil.createTo(vote));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        voteService.updateVote(user.id(), restaurantId);
    }
}