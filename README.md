# Android Football App

## Android Studio
If you don't have Android Studio installed please install it from [here](https://developer.android.com/studio/install).

When installed please create a virtual device following the instructions given [here](https://developer.android.com/studio/run/managing-avds).

When done properly then now you are ready to run the app. You don't have to install any additional dependencies.
The app has been tested on Pixel XL API 30 2 and Nexus 5X API 30.

## Concept Story
The app is meant for people who like to follow football major leagues. When opening the app the user is presented with their favourtite leagues. Using the button with the "+" icon the user is presented with major leagues. In that view the user can press the desired league and see it's details. In the details view the user can either go back to the main screen or save the selected league to favourites. User can also view details of the selected favourite leagues. The details view will show the user different data about the ongoing season in the league.

## Report
- Feature blocks
  - Persistent Storage (Firebase)
  - Web Servides (free REST API for [football](https://www.football-data.org/documentation/quickstart))

- Work
  - Since I worked alone all the development was made by me.
  - I think adding both feature blocks went well because at first sight they seemed difficult but in the end I didn't face any major setbacks.
  - I failed to add the third feature block. Didn't have enough time.

- Future Plans
  - I would add more leagues but since I am using a free API at the moment then I am not able to increase the API calls.
  - I would add more data that is displayed on the favourite league details view. I would add a calendar which would display the league's schedule with different games and results.
  - Add more genereal data. For example top scrores, top assists etc.
  - It should also be possible to add a news page that would display different news about the league (injuries, man of the matches, trade window news etc)

- Most Challenging Problem
  - For me the most challenging part was using Kotlin. I am used to writing Java so I had to google a lot of syntax problems and problems that came from small differences that Kotlin and Java have. These problems became quite annoying and demotivating at some points.

## OWASP Report
For testing the requirements I used the document provided [here](https://mas.owasp.org/MAS_checklist/).

- MSTG-STORAGE-3
  - No sensitive data is written to application logs. In my opinon it applies for every project. Logging is important for every project to find bugs more easily and it gives an overview for the developer what's happening when app is being used.
  - Yes, I fulfilled it. Currently I am only logging successful and unsuccessful requests.

- MSTG-CODE-6
  - The app catches and handles possible exceptions. Like the previous requirement, I think it applies to any project.
  - I did not fulfil it. I did not handle every error the system might get. Furthermore, those exceptions that I did handle aren't notifying the user about what went wrong.

