-- Insert Venues
INSERT INTO venues (venue_name, location) VALUES
                                              ('Cambodia Convention Center', 'Phnom Penh, Cambodia'),
                                              ('Koh Pich Conference Hall', 'Phnom Penh, Cambodia'),
                                              ('Siem Reap Exhibition Center', 'Siem Reap, Cambodia'),
                                              ('Olympic Stadium Hall', 'Phnom Penh, Cambodia');

-- Insert Events
INSERT INTO events (event_name, event_date, venue_id) VALUES
                                                          ('Tech Innovation Summit 2026', '2026-04-15 09:00:00', 1),
                                                          ('Cambodia Startup Festival', '2026-05-20 14:00:00', 2),
                                                          ('ASEAN Digital Conference', '2026-06-10 10:00:00', 1),
                                                          ('Music & Arts Festival', '2026-07-05 18:00:00', 3),
                                                          ('Business Leadership Forum', '2026-08-12 08:30:00', 4);

-- Insert Attendees
INSERT INTO attendees (attendee_name, email) VALUES
                                                 ('Sokha Chhin', 'sokha.chhin@gmail.com'),
                                                 ('Rithy Kim', 'rithy.kim@outlook.com'),
                                                 ('Sreyneath Lim', 'sreyneath.lim@yahoo.com'),
                                                 ('Vuthy Heng', 'vuthy.heng@gmail.com'),
                                                 ('Chantha Meas', 'chantha.meas@company.com'),
                                                 ('Sophal Pich', 'sophal.pich@tech.com'),
                                                 ('Malin Chea', 'malin.chea@gmail.com');

-- Insert Event-Attendee Relationships (Many-to-Many)
INSERT INTO event_attendee (event_id, attendee_id) VALUES
                                                       (1, 1),  -- Tech Summit → Sokha
                                                       (1, 2),  -- Tech Summit → Rithy
                                                       (1, 4),  -- Tech Summit → Vuthy
                                                       (1, 6),  -- Tech Summit → Sophal

                                                       (2, 1),  -- Startup Festival → Sokha
                                                       (2, 3),  -- Startup Festival → Sreyneath
                                                       (2, 5),  -- Startup Festival → Chantha
                                                       (2, 7),  -- Startup Festival → Malin

                                                       (3, 2),  -- ASEAN Conference → Rithy
                                                       (3, 4),  -- ASEAN Conference → Vuthy
                                                       (3, 6),  -- ASEAN Conference → Sophal

                                                       (4, 3),  -- Music Festival → Sreyneath
                                                       (4, 5),  -- Music Festival → Chantha
                                                       (4, 7),  -- Music Festival → Malin

                                                       (5, 1),  -- Business Forum → Sokha
                                                       (5, 2),  -- Business Forum → Rithy
                                                       (5, 4);  -- Business Forum → Vuthy