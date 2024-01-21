package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.comment.dto.CommentDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Builder
public class ItemDto {

    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Boolean available;

    private BookingShortDto lastBooking;

    private BookingShortDto nextBooking;

    private List<CommentDto> comments;
}
