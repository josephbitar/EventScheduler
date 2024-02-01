package org.events.models;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "events")
public class Event {
    @NotEmpty
    private String title;
    @NotNull(message = "start time cannot be null")
    private LocalDateTime startTime;
    private String description;
    @NotEmpty(message = "organizer cannot be empty")
    private String organizer;
    private String classId;
    @NotEmpty(message = "class name cannot be null")
    private String className;
    private String link;
    private boolean isOnline;
    private List<String> invitees;
    @NotNull(message = "end time cannot be null")
    private LocalDateTime endTime;
    private Long duration; // in minutes
}
