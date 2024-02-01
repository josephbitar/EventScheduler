CREATE TABLE IF NOT EXISTS classes (
    class_id SERIAL PRIMARY KEY,
    class_name VARCHAR(100) NOT NULL,
    location VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS events (
    event_id SERIAL PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    event_organizer VARCHAR(100) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    duration INT,
    invitees TEXT[],
    event_description VARCHAR(200),
    class_id INT REFERENCES classes(class_id)
);

CREATE TABLE IF NOT EXISTS schedule (
    schedule_id SERIAL PRIMARY KEY,
    event_id INT,
    class_id INT REFERENCES classes(class_id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events(event_id)
);

INSERT INTO classes (class_name, location)
VALUES ('Room_1', '3thd floor'),
       ('Room_2', '2ed floor'),
       ('Room_3', '1st floor'),
       ('Room_4', '1st floor');