package org.events.api;

import org.events.models.Event;
import org.events.models.Schedule;
import org.events.servies.IntervalPartitioningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class ScheduleController {
    private final IntervalPartitioningService intervalPartitioningService;

    public ScheduleController(IntervalPartitioningService intervalPartitioningService) {
        this.intervalPartitioningService = intervalPartitioningService;
    }

    @PostMapping(value = "v1.0/schedules")
    public Mono<ResponseEntity<Schedule>> createEvent(@RequestBody Event event) {
        return intervalPartitioningService.schedule(event)
                .map(response -> ResponseEntity.ok(response));
    }

    @GetMapping(value = "v1.0/schedules/{className}")
    public Flux<Schedule> createEvent(@PathVariable String className) {
        return intervalPartitioningService.findByClassName(className);
    }
}
