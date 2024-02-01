package org.events.servies;

import lombok.NonNull;
import org.events.exceptions.NotAvailableTimeSlot;
import org.events.models.ClassIdentifier;
import org.events.models.Event;
import org.events.models.Schedule;
import org.events.repositories.EventRepository;
import org.events.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class IntervalPartitioningService {
    // Lock that allows multiple reads simultaneously but only one write at a time
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Map<ClassIdentifier, LinkedList<Event>> classMap = new HashMap<>();
    private final EventRepository eventRepository;
    private final ScheduleRepository scheduleRepository;

    public IntervalPartitioningService(EventRepository eventRepository, ScheduleRepository scheduleRepository) {
        this.eventRepository = eventRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Mono<Schedule> schedule(Event event) {
        readWriteLock.writeLock().lock();
        try {
            return isAvailable(event.getClassName(), event.getStartTime(), event.getEndTime())
                    .flatMap(isAva -> {
                        if (isAva) {
                            Schedule schedule = Schedule.builder()
                                    .startTime(event.getStartTime())
                                    .endTime(event.getEndTime())
                                    .className(event.isOnline() ? "Online" : event.getClassName())
                                    .build();
                            return eventRepository.insert(event)
                                    .then(scheduleRepository.insert(schedule));

                        } else {
                            return Mono.error(new NotAvailableTimeSlot(String.format("Sorry, The selected time slot for class %s is not available. Please select another time slot", event.getClassId())));
                        }
                    });
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public Flux<Schedule> findByClassName(@NonNull String className) {
        return scheduleRepository.findByClassName(className);
    }
    private Mono<Boolean> isAvailable(String className, LocalDateTime startTime, LocalDateTime endTime) {
        return scheduleRepository.findByClassName(className)
                .filter(schedule -> !startTime.isAfter(schedule.getEndTime()) && !endTime.isBefore(schedule.getStartTime()))
                .map(schedule -> false)
                .defaultIfEmpty(true)
                .next();
    }
}
