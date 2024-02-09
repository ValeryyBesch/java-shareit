package ru.practicum.shareit.item.comment.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    public Long id;

    @NotNull
    @NotBlank
    private String text;

    private LocalDateTime created;

    private String authorName;
}
