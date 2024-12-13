package ru.didorenko.votingsystem.restaurant.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.restaurant.repository.RestaurantRepository;
import ru.didorenko.votingsystem.restaurant.utill.RestaurantUtill;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static ru.didorenko.votingsystem.restaurant.utill.RestaurantUtill.updateWithName;

@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    static final String REST_URL = "api/admin/restaurants";

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @GetMapping("/by-name")
    public Restaurant getByName(@RequestParam String name) {
        log.info("getByEmail {}", name);
        return repository.getExistedByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestParam @Valid String name) {
        log.info("create restaurant with name: {}", name);
        if (repository.getExistedByNameNot(name).isEmpty()) {
            Restaurant created = repository.save(RestaurantUtill.createNewWithName(name));
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL).build().toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Restaurant with name '" + name + "' already exists");
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestParam @Valid String newName) {
        log.info("update restaurant with id: {} ", id);
        Optional<Restaurant> existingRestaurant = repository.findById(id);
        if (existingRestaurant.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurant with id '" + id + "' not found");
        }
        if (repository.getExistedByNameNot(newName).isPresent() &&
                !existingRestaurant.get().getName().equalsIgnoreCase(newName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Another restaurant with name '" + newName + "' already exists");
        }
        Restaurant updated = repository.save(updateWithName(existingRestaurant.get(), newName));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }
}