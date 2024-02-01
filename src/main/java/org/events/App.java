package org.events;

import org.events.repositories.EventRepository;
import org.events.repositories.ScheduleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = "org.events")
@EnableReactiveMongoRepositories(basePackageClasses = {EventRepository.class, ScheduleRepository.class})
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
