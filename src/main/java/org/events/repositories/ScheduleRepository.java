package org.events.repositories;
import org.events.models.Schedule;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends ReactiveMongoRepository<Schedule, String> {
    @Query("{'className':  ?0}")
    Flux<Schedule> findByClassName(String className);

}
