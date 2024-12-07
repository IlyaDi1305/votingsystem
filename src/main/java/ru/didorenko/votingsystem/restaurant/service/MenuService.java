package ru.didorenko.votingsystem.restaurant.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.didorenko.votingsystem.restaurant.model.Menu;
import ru.didorenko.votingsystem.restaurant.repository.MenuRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MenuService {

    private MenuRepository menuRepository;

    public Menu getById(Integer id) {
        log.info("IN MenuService get {}", id);
        return menuRepository.getExisted(id);
    }

    public void save(Menu menu) {
        log.info("IN MenuService save {}", menu);
        menuRepository.save(menu);
    }

    public void delete(Integer id) {
        log.info("IN MenuService delete {}", id);
        menuRepository.deleteExisted(id);
    }

    public List<Menu> getAll() {
        log.info("IN MenuService getAll");
        return menuRepository.findAll();
    }
}
