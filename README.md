# Doctor Practice

An attempt to familiarise with Event Sourcing and CQRS patterns. 

## Domain (fictional)

We are working for a startup developing modern software for healthcare.
The part of the domain we are responsible provides capabilities related to
appointment scheduling.

Our software is used mostly by small local practices. Each of these practices
employs between 1 and 5 doctors on a full-time or part-time basis. Each doctor can
spend up to 6 hours a day seeing patients. These 6 hours are divided into 10 minutes
slots that can be then booked by the patients.

All slots are scheduled in advance. Slots scheduled by a single doctor cannot overlap
with each other.

The list of available slots returns all slots for a single day, grouped by a doctor
and ordered by the start time. The list of patient slots returns a list of all future
slots booked by the patient.

A single slot can be booked by only one patient and if the patient changes his mind
(cancels slot booking), then the slot becomes available again. The patient also has
an option to book a double slot. If there are any two adjacent slots available, then
it's possible to book both of them at the same time. If one of them gets booked in
the meantime, then the whole booking should fail.

If a doctor no longer can see patients on a given day, then the schedule for the day
can be cancelled. This means that all the visits and all slots need to be cancelled
as well, and all patients notified via email.

A single patient should not be allowed to book more than 10 slots in the month. If
such a situation happens then the last booked slot should be automatically cancelled,
and patient notified about this fact via email.

## Solution

A solution for the given problem domain would be to implement a new application,
responsible for the `Scheduling` bounded context.

This bounded context is a good candidate to be implemented by following patterns
such as [Event Sourcing](https://www.eventstore.com/event-sourcing#What-is-Event-Sourcing)
and [CQRS](https://www.eventstore.com/event-sourcing#CQRS).

The codebase is mainly written in [Kotlin](https://kotlinlang.org/) and the application
consists of:
- an API server backed up by [Spring Boot](https://spring.io/projects/spring-boot).
- a persistence layer for the write-models backed up by [EventStoreDB](https://www.eventstore.com/),
- a persistence layer for the read-models backed up by [MongoDB](https://www.mongodb.com/).

## Testing Strategies

We want to make sure out test suite remains fast, but still maintain a high degree of confidence 
that our write and read-models behave as expected.

In order to achieve that we will exercise both write and read-models with unit tests,
by introducing in-memory repositories implementations.

Additionally, we want a layer of system level tests to cover the main journeys end-to-end,
leveraging docker containers in order to spin up the required infrastructure.

Note that system level tests have to be designed to take care of eventual consistency,
that is to say some assertion will have to be implemented in a retry fashion.

In this specific case we are offloading that responsibility to a third party testing library,
namely [Awaitability](https://github.com/awaitility/awaitility).

### Write-Model Scenarios

Our write-model scenarios will be built around event sourced `Aggregates`.

For the definition of a scenario of an event sourced `Aggregate`, we can rely on the following format:

```gherkin
Given 0 or more events have happened in order
When a command is issued
Then 1 or more events (or an error) are raised in order
```

For brevity, we could think about specifying a scenario in this way:

```gherkin
Given (Slot #1 Scheduled, Slot #1 Booked)
When (Book Slot #1)
Then (Slot Already Booked)
```

### Read-Model Scenarios

Our read-model scenarios will be built around `Projections`.

For the definition of a scenario of a `Projection`, we can rely on the following format:

```gherkin
Given 0 or more events have been projected in order
When a query is issued
Then 1 or more updated read models is available
```

For brevity, we could think about specifying a scenario in this way:

```gherkin
Given (Slot #1 Scheduled, Slot #2 Scheduled, Slot #2 Booked)
When (Find All Available Slots)
Then (Available Slot #1)
```

### Run Tests

As mentioned in the previous section we include system level tests in our test suite,
hence we need to spin up the required infrastructure before running the tests.

In order to run our test suite issue the following command in the terminal, from the root of the project:

```shell
docker-compose rm -f # clean residual state for infra (if any)
docker-compose up -d # set up infra in the background
./gradlew clean test
docker-compose down # teardown infra
```

or just use the provided script:

```shell
./scripts/acceptance.sh
```

## Sources

This project is heavily inspired by the training material provided by `EventStoreDB`, especially [this](https://github.com/EventStore/training-advanced-java) repository.

## License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](./LICENSE)
