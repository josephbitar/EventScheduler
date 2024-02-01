package org.events.servies;

import org.events.exceptions.NotAvailableTimeSlot;
import org.events.models.Event;
import org.events.models.Schedule;
import org.events.repositories.EventRepository;
import org.events.repositories.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class IntervalPartitioningServiceTest {
    @Autowired
    private IntervalPartitioningService intervalPartitioningService;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private EventRepository eventRepository;

    @BeforeEach
    public void start() {
        Mockito.when(eventRepository.insert(any(Event.class))).thenReturn(Mono.empty());
        Mockito.when(scheduleRepository.insert(any(Schedule.class))).thenReturn(Mono.empty());
    }

    @Test
    void schedule_test_when_isOverlap_then_throw_notAvailableException() {
        // Set up
        Event event1 = Event.builder()
                .className("class_1")
                .startTime(LocalDateTime.of(2023, 12,12, 13, 00, 00))
                .endTime(LocalDateTime.of(2023, 12,12, 14, 00, 00))
                .build();
        Schedule schedule = Schedule.builder()
                .className("class_1")
                .startTime(LocalDateTime.of(2023, 12,12, 13, 15, 00))
                .endTime(LocalDateTime.of(2023, 12,12, 14, 15, 00))
                .build();

        // When.. Then
        Mockito.when(scheduleRepository.findByClassName("class_1")).thenReturn(Flux.just(schedule));

        StepVerifier.create(intervalPartitioningService.schedule(event1))
                .expectError(NotAvailableTimeSlot.class)
                .verify();
    }

    @Test
    void schedule_test_when_isOverlap_then_throw_notAvailableException_2() {
        // Set up
        Event event1 = Event.builder()
                .className("class_1")
                .startTime(LocalDateTime.of(2023, 12,12, 13, 00, 00))
                .endTime(LocalDateTime.of(2023, 12,12, 14, 00, 00))
                .build();
        Schedule schedule = Schedule.builder()
                .className("class_1")
                .startTime(LocalDateTime.of(2023, 12,12, 13, 00, 00))
                .endTime(LocalDateTime.of(2023, 12,12, 14, 00, 00))
                .build();

        // When.. Then
        Mockito.when(scheduleRepository.findByClassName("class_1")).thenReturn(Flux.just(schedule));

        StepVerifier.create(intervalPartitioningService.schedule(event1))
                .expectError(NotAvailableTimeSlot.class)
                .verify();
    }

    @Test
    void schedule_test_when_no_isOverlap_then_should_be_scheduled() {
        // Set up
        Event event1 = Event.builder()
                .className("class_1")
                .startTime(LocalDateTime.of(2023, 12,12, 14, 01, 00))
                .endTime(LocalDateTime.of(2023, 12,12, 15, 00, 00))
                .build();
        Schedule schedule = Schedule.builder()
                .className("class_1")
                .startTime(LocalDateTime.of(2023, 12,12, 13, 15, 00))
                .endTime(LocalDateTime.of(2023, 12,12, 14, 00, 00))
                .build();

        // When.. Then
        Mockito.when(scheduleRepository.findByClassName(any())).thenReturn(Flux.just(schedule));

        // Run
        StepVerifier.create(intervalPartitioningService.schedule(event1))
                .expectComplete();
    }
}