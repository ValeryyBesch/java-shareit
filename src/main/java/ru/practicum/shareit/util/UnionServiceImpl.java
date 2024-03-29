package ru.practicum.shareit.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UnionServiceImpl implements UnionService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;


    @Override
    public void checkUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(User.class, userId + " не найдено.");
        }
    }

    @Override
    public void checkItem(Long itemId) {

        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException(Item.class, itemId + " не найдено.");
        }
    }

    @Override
    public void checkBooking(Long bookingId) {

        if (!bookingRepository.existsById(bookingId)) {
            throw new NotFoundException(Booking.class, bookingId + " не найдено.");
        }
    }

    @Override
    public void checkRequest(Long requestId) {

        if (!itemRequestRepository.existsById(requestId)) {
            throw new NotFoundException(ItemRequest.class, "Request id " + requestId + " not found.");
        }
    }

    @Override
    public PageRequest checkPageSize(Integer from, Integer size) {

        if (from == 0 && size == 0) {
            throw new NotValidException("\"size\" and \"from\"must be not equal 0");
        }

        if (size <= 0) {
            throw new NotValidException("\"size\" must be greater than 0");
        }

        if (from < 0) {
            throw new NotValidException("\"from\" must be greater than or equal to 0");
        }
        return PageRequest.of(from / size, size);
    }
}
