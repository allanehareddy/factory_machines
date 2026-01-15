Overview

# Backend service to ingest, validate, deduplicate, and aggregate factory machine events

# Designed to handle out-of-order, duplicate, and concurrent event ingestion safely

# Built using Spring Boot, JPA, and transactional database guarantees

1. Architecture

Layered Spring Boot architecture

Clear separation of concerns

Layers

Controller layer

Exposes REST APIs for batch ingestion

Service layer

EventIngestionService contains core business logic

Validation layer

EventValidator validates domain rules

Utility layer

PayloadComparator compares event payloads

Repository layer

EventRepository handles database persistence

Flow

Client → Controller → Service → Repository → Database

2. Deduplication & Update Logic

eventId uniquely identifies an event

Only one record per eventId is allowed

Payload comparison

Compares:

# eventTime

# machineId

# durationMs

# defectCount

Decision rules

No existing eventId → insert new record

Same payload → deduped

Different payload + newer receivedTime → update existing record

Different payload + older receivedTime → ignored

Winning record

The event with the latest receivedTime is considered authoritative

3. Thread Safety

Thread safety is achieved using database guarantees, not JVM locks

Why it is thread-safe

Unique constraint on eventId

@Transactional ingestion method

Database is the single source of truth

No shared mutable in-memory state

Result

Safe concurrent ingestion

No race conditions

No duplicate records

4. Data Model

Event data stored as a single entity

EventEntity fields

eventId (primary key)

factoryId

lineId

machineId

eventTime

receivedTime

durationMs

defectCount

5. Performance Strategy

Designed to process 1000+ events per second

Performance optimizations

Batch ingestion endpoint

Indexed queries on eventId, machineId, and eventTime

O(n) ingestion logic

Minimal object creation

No blocking locks or synchronization

6. Edge Cases & Assumptions

Handled edge cases

Duplicate eventId

Same payload re-ingestion

Payload updates with newer timestamps

Older payloads ignored

defectCount = -1 excluded from defect aggregation

Start time inclusive, end time exclusive

Future eventTime rejected

Invalid duration rejected

Assumptions

eventId is globally unique

Events may arrive out of order

receivedTime determines conflict resolution

Tradeoffs

Prioritized correctness and safety over aggressive caching

Chose transactional consistency over eventual consistency

7. Setup & Run Instructions

Prerequisites

Java 17+

Maven

SQL database (H2 / PostgreSQL / MySQL)

Run application

./mvnw spring-boot:run


Run tests

./mvnw clean test

8. Testing Strategy

Unit and integration tests written using JUnit 5

Tests cover:

Deduplication

Update logic

Ignored payloads

Validation failures

Time boundary correctness

Thread safety via concurrent ingestion

9. Future Improvements

Inject Clock for fully deterministic time-based testing

Bulk database operations for large batches

Metrics and monitoring

Optimistic locking/versioning

Retry handling for transient failures

Horizontal scalability enhancements

Summary

The system is designed for correctness, concurrency safety, and performance

Clean separation of responsibilities

Deterministic behavior under concurrent ingestion

Easily extensible and testable architecture
