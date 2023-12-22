package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    void addItem(long owner, Item item);

    Item updateItem(Item item, long itemId, long userId);

    Item getItemById(long itemId);

    List<Item> getItemsUser(long userId);

    List<Item> searchItem(String text);

}
