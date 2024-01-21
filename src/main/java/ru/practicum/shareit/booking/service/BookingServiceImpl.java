package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.util.UnionService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UnionService unionService;

    @Transactional
    @Override
    public BookingOutDto addBooking(BookingDto bookingDto, long userId) {

        unionService.checkItem(bookingDto.getItemId());
        Item item = itemRepository.findById(bookingDto.getItemId()).get();

        unionService.checkUser(userId);
        User user = userRepository.findById(userId).get();

        Booking booking = BookingMapper.returnBooking(bookingDto);
        booking.setItem(item);
        booking.setBooker(user);

        if (item.getOwner().equals(user)) {
            throw new NotFoundException(User.class, "Владелец " + userId + " не может забронировать свой собственный элемент");
        }

        if (!item.getAvailable()) {
            throw new NotValidException("Элемент " + item.getId() + " забронирован");
        }

        if (booking.getStart().isAfter(booking.getEnd())) {
            throw new NotValidException("Начальная дата не может быть позже конечной даты");
        }

        if (booking.getStart().isEqual(booking.getEnd())) {
            throw new NotValidException("Начальная дата не может быть равной конечной дате");
        }


        bookingRepository.save(booking);

        return BookingMapper.returnBookingDto(booking);
    }

    @Transactional
    @Override
    public BookingOutDto approveBooking(long userId, long bookingId, Boolean approved) {

        unionService.checkBooking(bookingId);
        Booking booking = bookingRepository.findById(bookingId).get();

        if (booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException(User.class, "Только владелец"  + userId +
                    " элемента может изменять статус бронирования");
        }

        if (approved) {
            if (booking.getStatus().equals(Status.APPROVED)) {
                throw new NotValidException("Некорректный запрос на обновление статуса");
            }
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }

        bookingRepository.save(booking);
        return BookingMapper.returnBookingDto(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public BookingOutDto getBookingById(long userId, long bookingId) {

        unionService.checkBooking(bookingId);
        Booking booking = bookingRepository.findById(bookingId).get();

        unionService.checkUser(userId);

        if (booking.getBooker().getId() == userId || booking.getItem().getOwner().getId() == userId) {
            return BookingMapper.returnBookingDto(booking);
        } else {
            throw new NotFoundException(User.class, "Для получения информации о бронировании могут обращаться " +
                    "только автомобиль бронирования или владелец элемента " + userId);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingOutDto> getAllBookingsByBookerId(long userId, String state) {

        unionService.checkUser(userId);

        List<Booking> bookings = null;

        State bookingState = State.getEnumValue(state);

        switch (bookingState) {
            case ALL:
                bookings = bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
                break;
            case CURRENT:
                bookings = bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartAsc(userId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case PAST:
                bookings = bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case FUTURE:
                bookings = bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case WAITING:
                bookings = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
                break;

        }
        return BookingMapper.returnBookingDtoList(bookings);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingOutDto> getAllBookingsForAllItemsByOwnerId(long userId, String state) {

        unionService.checkUser(userId);

        if (itemRepository.findByOwnerId(userId).isEmpty()) {
            throw new NotValidException("У пользователя нет элементов для бронирования");
        }

        List<Booking> bookings = null;

        State bookingState = State.getEnumValue(state);

        switch (bookingState) {
            case ALL:
                bookings = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId);
                break;
            case CURRENT:
                bookings = bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartAsc(userId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case PAST:
                bookings = bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case FUTURE:
                bookings = bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case WAITING:
                bookings = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
                break;
        }
        return BookingMapper.returnBookingDtoList(bookings);
    }
}

