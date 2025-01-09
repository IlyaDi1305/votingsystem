package ru.didorenko.votingsystem.web.vote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.app.AuthUser;
import ru.didorenko.votingsystem.service.VoteService;
import ru.didorenko.votingsystem.to.VoteTo;
import ru.didorenko.votingsystem.utill.VoteUtil;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = VoteUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteUserController {
    static final String REST_URL = "/api/user/votes";

    private final VoteService voteService;

    @GetMapping
    public List<VoteTo> getAllByUserId(@AuthenticationPrincipal AuthUser user) {
        log.info("Fetching all votes for user with id {}", user.id());
        return voteService.getAllByUserId(user.id());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VoteTo> createVoteWi(@RequestParam Integer restaurantId,
                                               @AuthenticationPrincipal AuthUser user) {
        log.info("Creating vote for restaurantId {} by user with id {}", restaurantId, user.id());
        return ResponseEntity.ok(VoteUtil.createTo(voteService.createVote(restaurantId, user.id())));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@RequestParam Integer restaurantId,
                           @AuthenticationPrincipal AuthUser user) {
        log.info("Updating vote for restaurantId {} by user with id {}", restaurantId, user.id());
        voteService.updateVote(restaurantId, user.id());
    }
}
