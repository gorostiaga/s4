# Super Simple Scheduling System

## Used technologies/frameworks
The app is divided into these layer (Controller, Service, Data Model)
- I am using Spring Data JPA so that I can map the Classes directly to the DB 
The connector for the DB is address to H2
- Devtool library for making easier the reboot of the app every time I make changes
- Project lombok for reducing the boilerplate code
- Spring validation to use the annotation @NotNull 
- Open API for the API documentation 

## This App makes these operations
- Create/Edit/Delete Student
- Create/Edit/Delete Course
- Browse list of all Student
- Browse list of all Courses
- View all Students assigned to a Courses
- View all Courses assigned to a Student
- Search Student and Courses for a given Course and Student respectively, if no association found it returns null.

## API Documentation 
go to http://localhost:8080/swagger-ui.html 




