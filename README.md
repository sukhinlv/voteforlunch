### Intro

Hi! Here you can find demo app that was written like test task.

You can tackle working app with such curl scripts:
...

or use Swagger link ...

### Technical requirement

Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) without frontend.

The task is: Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote for a restaurant they want to have lunch at today
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides a new menu each day.

As a result, provide a link to GitHub repository. It should contain the code, README.md with API documentation and couple curl commands to test it (better - link to Swagger).

P.S.: Make sure everything works with the latest version that is on github :)
P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

### Used technologies

For simplicity, I use H2 in-memory database, that fills with some test data using InitializeData#commandLineRunner
ORM framework is Spring Data JPA
Entities are mapped to DTOs using simple mapper classes without using Mapstruct or other complex libraries
Application uses ehCache for caching. Due to the logic of application only MenuService use cache for menus. Because I think that it will be the narrowest place.

Cached: menu, votes distribution

no security implemented, just mock in SecurityUtil

### Project structure

### Actions required to run the application

h2 db used, no actions needed
you can set port in application.yml, ... parameter
sample data populated in ru.jsft.voteforlanch.Config.InitializeData#commandLineRunner
no security implemented
