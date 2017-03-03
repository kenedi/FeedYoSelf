# FeedYoSelf

A final project for WPI CS 4518 C17: Mobile and Ubiquitous Computing. Completed by Nick Bradford, Kenedi Heather, Dan Seaman. 

Check out our demo video [here](https://www.youtube.com/watch?v=d7pIhCt6tjY). Note that their have been significant modifications since our final in-class presentation.

## App Usage: 
* Upon opening, you'll be given a Login Screen. The active button will be labeled "Log Into Your Gmail". Click that one. 
* The other two buttons will activate. You can access the calendar list view or the map view.
* From the calendar list view, you can access the map view with the map button. 
* From the map view, you can go back to the previous screen using you device's back button.

## Class Descriptions:

	EmailActvity: Launcher activity. Accesses the gmail account of the user, pulls email bodies, sends them to FoodNLP

	FeedNLP: NLP part of the project. Searches for time strings, locations, and food types in email message bodies.

	FoodEvent: Events that contain the food type, time and date, and location. Displayed on the calendar list view.

	MainActivity: This is infact our calendar list view.

	FeedYoSelfMap: The map view that shows where on campus the food is available. 

