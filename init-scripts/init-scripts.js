db.createUser({
    user: "adminUser",
    pwd: "adminPassword",
    roles: [ { role: "root", db: "admin" } ]
})

db = db.getSiblingDB('mydatabase');

// Create a collection for classes
db.createCollection('classes');

// Create a collection for events
db.createCollection('events');

// Create a collection for schedule
db.createCollection('schedule');

// Insert sample data into the "classes" collection
db.classes.insertMany([
    { class_name: "Room 1", location: "1st floor" },
    { class_name: "Room 2", location: "2ed floor" },
    { class_name: "Room 3", location: "3ed floor" }
]);