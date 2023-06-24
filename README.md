# Average_app
Android app that calculates average for student based on subject count provided by user.
Application is written in Java and in Android Studio (Electric Eel 2022.1.1) and features:
- EditText is validated during focus change (the name and surname is non-empty literal such as surname),
- EditText cantaining subject count have numeric keyboard and must be between [5-15] to be able to move to second activity,
- Is any of the EditText is incorrect when going out there is toast message that the user wrote wrong value,
- The button that goes to second activity is only visilbe when all the values are correct  (and forwards subject count as bundle),
- Second activity uses recycler view to display variable count of subjects as a form,
- Second activity calculates average and then forward that infomrmation as bundle to first activity (where the end-button is displayed),

Development environment:
- Java SE Runtime Environment (build 1.8.0_371-b11)
- Android Studio Electric Eel | 2022.1.1

# How to compile and run
To run application:
1. Download zip package
2. Extract package and open using Android Studio
3. If there is error with versions (pre Electric Eel) you should change version of the IDE in one of the gradle files and rebuild
4. Build application and run (either on VM Android or physical device, the development device is Samsung A53)

# Screenshots:
1. Application showing in app tray with specific icon:
<img src="https://github.com/RobertNeat/Average_app/blob/main/pictures_res/app_tray.png" width="200"/>
1. Application launch:
<img src="https://github.com/RobertNeat/Average_app/blob/main/pictures_res/launcher_screen.png" width="200"/>
1. First view without any values inputted by user:
<img src="https://github.com/RobertNeat/Average_app/blob/main/pictures_res/first_view_empty.png" width="200"/>
1. When first view is filled up there is button appearing that launches second activity:
<img src="https://github.com/RobertNeat/Average_app/blob/main/pictures_res/first_view_filled.png" width="200"/>
1. Second view uses recycler view that shows variable number of subjects:
<img src="https://github.com/RobertNeat/Average_app/blob/main/pictures_res/second_view_filled.png" width="200"/>
