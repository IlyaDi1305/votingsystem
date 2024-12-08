package ru.didorenko.votingsystem.restaurant.repository;

import org.springframework.stereotype.Repository;
import ru.didorenko.votingsystem.common.BaseRepository;
import ru.didorenko.votingsystem.restaurant.model.MenuItem;

@Repository
public interface MenuItemRepository extends BaseRepository<MenuItem> {
}