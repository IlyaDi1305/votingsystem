package ru.didorenko.votingsystem.restaurant.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.votingsystem.restaurant.model.Menu;
import ru.didorenko.votingsystem.restaurant.model.Restaurant;
import ru.didorenko.votingsystem.restaurant.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurants/menus")
@AllArgsConstructor
public class AdminMenuController {

    private MenuService menuService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> getMenu(@PathVariable("id") Integer menuId) {
        if (menuId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Menu menu = menuService.getById(menuId);

        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> saveMenu(@RequestBody @Valid Menu menu) {
        HttpHeaders headers = new HttpHeaders();

        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.menuService.save(menu);
        return new ResponseEntity<>(menu, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> updateMenu(@RequestBody @Valid Menu menu) {
        HttpHeaders headers = new HttpHeaders();

        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.menuService.save(menu);
        return new ResponseEntity<>(menu, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> deleteMenu(@PathVariable("id") Integer id) {
        Menu menu = this.menuService.getById(id);

        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.menuService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Menu>> getAll() {
        List<Menu> menus = this.menuService.getAll();

        if (menus.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
}
