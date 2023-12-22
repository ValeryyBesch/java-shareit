package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * TODO Sprint add-controllers.
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;
    private final ItemMapper mapper;

    @PostMapping
    public ItemDto add(@RequestHeader("X-Sharer-User-Id") long userId,
                       @RequestBody @Valid ItemDto itemDto) {

        Item item = mapper.returnItem(itemDto);
        service.addItem(userId, item);
        return mapper.returnItemDto(item);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                              @RequestBody ItemDto itemDto,
                              @PathVariable Long itemId) {

        Item item = mapper.returnItem(itemDto);
        Item updateItem = service.updateItem(item, itemId, userId);
        return mapper.returnItemDto(updateItem);
    }

    @GetMapping("/{itemId}")
    public ItemDto getUser(@PathVariable Long itemId) {
        return mapper.returnItemDto(service.getItemById(itemId));
    }

    @GetMapping
    public List<ItemDto> getAllItemsUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        return service.getItemsUser(userId)
                .stream()
                .map(mapper::returnItemDto)
                .collect(toList());
    }

    @GetMapping("/search")
    public List<ItemDto> getSearchItem(String text) {
        return service.searchItem(text)
                .stream()
                .map(mapper::returnItemDto)
                .collect(toList());
    }
}