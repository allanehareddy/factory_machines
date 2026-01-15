# Ingestion Benchmark

This document describes the performance benchmark conducted for the event ingestion service.

## Test Environment

**Machine**
- CPU: Intel Core i5 (quad-core)
- RAM: 8 GB
- OS: Windows 10 (64-bit)
- Java Version: Java 17
- Database: H2 (in-memory)
- Build Tool: Maven

## Benchmark Objective

Measure the time taken to ingest a single batch of **1000 events** while applying:
- Validation
- Deduplication
- Conditional updates
- Transactional persistence

## Benchmark Setup

A batch of 1000 `EventRequestDto` objects was generated with:
- Unique `eventId`
- Same `machineId`
- Valid `eventTime` within range
- Mixed `defectCount` values

The ingestion was executed inside a single transaction.

## Command Used

```bash
./mvnw test
