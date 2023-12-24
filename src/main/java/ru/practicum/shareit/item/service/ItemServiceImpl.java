package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final UserDao userDao;

    @Override
    public void addItem(long userId, Item item) {
        userDao.getUser(userId);
        item.setOwner(userId);
        itemDao.add(item);
    }

    @Override
    public Item updateItem(Item item, long itemId, long userId) {
        item.setId(itemId);
        item.setOwner(userId);
        if (!getItemsUser(userId).contains(item)) {
            throw new NotFoundException("пользователь с id " + userId + " не найден");
        }
        return itemDao.update(userId, item);
    }

    @Override
    public Item getItemById(long itemId) {
        return itemDao.get(itemId);
    }

    @Override
    public List<Item> getItemsUser(long userId) {
        return itemDao.getItemsByUserId(userId);
    }

    @Override
    public List<Item> searchItem(String string) {
        return itemDao.search(string);
    }
}
