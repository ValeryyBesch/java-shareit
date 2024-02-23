package ru.practicum.shareit.booking.dto;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingShortDtoTest {

    @Test
    public void testBookingShortDtoBuilder() {
        Long id = 1L;
        Long bookerId = 123L;
        LocalDateTime start = LocalDateTime.of(2023, 10, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 17, 10, 0);

        BookingShortDto bookingShortDto = BookingShortDto.builder()
                .id(id)
                .bookerId(bookerId)
                .start(start)
                .end(end)
                .build();

        assertThat(bookingShortDto.getId()).isEqualTo(id);
        assertThat(bookingShortDto.getBookerId()).isEqualTo(bookerId);
        assertThat(bookingShortDto.getStart()).isEqualTo(start);
        assertThat(bookingShortDto.getEnd()).isEqualTo(end);
    }
}
