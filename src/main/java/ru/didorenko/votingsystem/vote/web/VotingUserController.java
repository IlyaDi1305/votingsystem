package ru.didorenko.votingsystem.vote.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.app.AuthUser;
import ru.didorenko.votingsystem.vote.model.Vote;
import ru.didorenko.votingsystem.vote.service.VotingService;
import java.util.List;

@RestController
@RequestMapping("/api/user/votes")
public class VotingUserController {
    @Autowired
    private VotingService votingService;

    @PostMapping("/{restaurantId}")
    public ResponseEntity<?> vote(@PathVariable Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        Vote vote = votingService.castVote(user.id(), restaurantId);
        return ResponseEntity.ok(vote);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> votes = this.votingService.getAll();
        if (votes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }
}
