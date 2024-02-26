package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;


import lombok.*;

@Data
@Builder
public class BookingDto {

	private Long itemId;

	@FutureOrPresent(message = "start may be in the present or future")
	private LocalDateTime start;

	@Future(message = "end may be in the future")
	private LocalDateTime end;

}
