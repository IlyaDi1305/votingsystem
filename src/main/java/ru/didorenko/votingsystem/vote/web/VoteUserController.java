package ru.didorenko.votingsystem.vote.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.didorenko.votingsystem.app.AuthUser;
import ru.didorenko.votingsystem.restaurant.repository.RestaurantRepository;
import ru.didorenko.votingsystem.user.repository.UserRepository;
import ru.didorenko.votingsystem.vote.model.Vote;
import ru.didorenko.votingsystem.vote.repository.VoteRepository;
import ru.didorenko.votingsystem.vote.to.VoteTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.didorenko.votingsystem.vote.utill.VoteUtil.createToList;

@Slf4j
@RestController
@RequestMapping(value = VoteUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteUserController {
    static final String REST_URL = "/api/user/votes";

    @Autowired
    protected VoteRepository voteRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    protected UserRepository userRepository;


    @GetMapping
    @Cacheable(value = "votesCache", key = "#user.id")
    public List<VoteTo> getAllByUserId(@AuthenticationPrincipal AuthUser user) {
        log.info("getAllByUserId");
        return createToList(voteRepository.getAllByUserId(user.id()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "votesCache", key = "#user.id")
    public ResponseEntity<Vote> create(@RequestParam  Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        LocalDate today = LocalDate.now();
        log.info("create vote with restaurantId {}" , restaurantId);
        voteRepository.findByUserIdAndDate(user.id(), today)
                .ifPresent(existingVote -> {
                    throw new IllegalStateException("User has already voted today.");
                });
        Vote created = voteRepository.save(new Vote(userRepository.getExisted(user.id()),
                restaurantRepository.getExisted(restaurantId)));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = "votesCache", key = "#user.id")
    public void update(@RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser user) {
        log.info("update vote with restaurantId {}" , restaurantId);
        LocalDate today = LocalDate.now();
        Vote existingVote = voteRepository.findByUserIdAndDate(user.id(), today)
                .orElseThrow(() -> new IllegalStateException("No vote found for today."));
        LocalTime deadline = LocalTime.of(23, 59);
        if (LocalTime.now().isAfter(deadline)) {
            throw new IllegalStateException("Vote cannot be changed after 11:00 AM.");
        }
        voteRepository.updateAndSave(existingVote, restaurantRepository.getExisted(restaurantId));
    }
}