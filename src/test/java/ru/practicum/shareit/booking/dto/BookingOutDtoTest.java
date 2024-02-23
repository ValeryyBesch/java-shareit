package ru.practicum.shareit.booking.dto;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;



class BookingOutDtoTest {

    @Test
    public void testBookingOutDtoBuilder() {
        Long id = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 10, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 17, 10, 0);
        Status status = Status.APPROVED;
        UserDto userDto = UserDto.builder()
                .id(123L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        ItemDto itemDto = ItemDto.builder()
                .id(456L)
                .name("Test Item")
                .description("Test description")
                .build();

        BookingOutDto bookingOutDto = BookingOutDto.builder()
                .id(id)
                .start(start)
                .end(end)
                .status(status)
                .booker(userDto)
                .item(itemDto)
                .build();

        assertThat(bookingOutDto.getId()).isEqualTo(id);
        assertThat(bookingOutDto.getStart()).isEqualTo(start);
        assertThat(bookingOutDto.getEnd()).isEqualTo(end);
        assertThat(bookingOutDto.getStatus()).isEqualTo(status);
        assertThat(bookingOutDto.getBooker()).isEqualTo(userDto);
        assertThat(bookingOutDto.getItem()).isEqualTo(itemDto);
    }
}