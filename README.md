# InPost Lockers Route Planner

## Author

- **Name:** Adam Nowak
- **Email:** adamnowakhomm@gmail.com

## Overview

For my technical assessment I built a route planning tool. It displays a list of every parcel 
locker within a default, 5km, range from current location on a map and analyzes the quickest route 
to visit them. After selecting lockers the user receives a link to a Google Maps route with 
selected stops.

## Demo & Description
The app displays a list of parcel lockers filtered based on the distance of them from users 
location, lets the user select up to 9 lockers on the map and analyzes the shortest path 
between these points.

The algorithm for route planning is recursive and decides the next point based on the distance in 
straight line between each point. I also decided to handle creating the Google Maps link on the server 
because doing it on the Angular app would introduce unnecessary traffic with sending the selected lockers back
and forth in order to get a single link.

The design pattern I chose for the backend was MVC, due to my familiarity with it and
I found it to be very intuitive for searching through the project files.

For filtering packages I used the Java Stream API which shortened boilerplate code and
increased readability, records added into the database are already filtered by the ```PL``` country code. 
This can be changed quickly inside the code, but I decided to leave it as is because I was only 
interested in the lockers nearby to me. The app features scheduled tasks with CRON which update 
the database contents every hour to ensure up-to-date data was served to users. To download data 
from the points API I created an ApiClient class with some features from the concurrency API to 
fetch the data faster, whole process takes about 2 minutes.

#### Landing page
![main_app_page](/screenshots/main-page.png)

#### Landing page with lockers selected
![main_app_page_with_lockers_selected](/screenshots/lockers-selected.png)

#### Popup containing a link to Google Maps
![link_popup](/screenshots/link-popup.png)

#### Popup with a message
![message_popup](/screenshots/no-link-popup.png)

#### Selected route in Google Maps
![selected_route_in_google_maps](/screenshots/google-maps-route.png)

## Technologies

Backend:
- Java 21, SpringBoot 4

Frontend:
- TypeScript, Angular 20

Database:
- PostgreSQL

Tools:
- Bruno (API testing)
- Docker

## How to run

### Prerequisites
- Java 21
- NodeJS
- NPM
- Docker & Docker Compose

### Build & run

```bash
# Building the whole app:
 git clone https://github.com/AdaskoKwi/inpost-technical-assessment-2026.git
 cd inpost-technical-assessment-2026/
 docker-compose up -d --build
```

After that go to ```http://localhost:4200/``` in your browser to open the web app. 

## What I would do with more time

The most important thing I'd do if I had more time would be to implement a better route planning
algorithm that takes roads into account and remake a better web UI with reactive design for good
view on mobile devices. 

## AI usage

I used Google's Gemini to quickly analyze error logs and help with JSON payloads for API testing 
in Bruno. I also used it as a quick TypeScript documentation lookup tool 
and to get a lot of help with containerization.

## Anything else?

Updating the data contained in the database happens every hour because I found it to be a good interval of time
for having latest data with the amount of time it took to read the API which was about 2 minutes.