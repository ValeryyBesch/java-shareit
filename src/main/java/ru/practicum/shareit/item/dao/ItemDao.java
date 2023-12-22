package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemDao {

    Item get(long itemId);

    List<Item> getAll();

    Item add(Item item);

    Item update(long userId, Item item);

    List<Item> getItemsByUserId(long userId);

    List<Item> search(String text);

}