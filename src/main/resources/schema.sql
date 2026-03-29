-- Venues Table
CREATE TABLE venues (
    venue_id SERIAL PRIMARY KEY,
    venue_name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL
);

-- Events Table
CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    event_name VARCHAR(150) NOT NULL,
    event_date DATE NOT NULL,
    venue_id INTEGER NOT NULL,
    FOREIGN KEY (venue_id) REFERENCES venues(venue_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Attendees Table
CREATE TABLE attendees (
    attendee_id SERIAL PRIMARY KEY,
    attendee_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Event_Attendee Junction Table (Many-to-Many relationship)
CREATE TABLE event_attendee (
    attendee_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    PRIMARY KEY (attendee_id, event_id),
    FOREIGN KEY (attendee_id) REFERENCES attendees(attendee_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events(event_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);