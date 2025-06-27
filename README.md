My tasks is an android applicaiton built in Kotlin language
-> MVVM structure is used throughout the applicaiton, using clean code
-> Room database has been implemented in it

the app has mainly 3 screens 
1. Login Screen
2. Home Screen
3. Add/Edit task screen

the following are the features added
--> Login screen
    1. Password visibility toggle
    2. Validations for email and password (password is 1111)
    3. Lottie animation added for good user experience

--> Home Screen
    1. List Of Task from Room database
    2. Filter for the tasks on top of the screen
    3. Reactive list according to filters
    4. Floating Action Button To Add new tasks
    5. Click on existing task to view details or edit
    6. Lottie animation is implemented to show for empty list for better user experience

--> Add/Edit task screen
    1. Form for adding the task
    2. Validation on submit button for deadline and title
    3. Data picker for the deadline of task
    4. Chip selection for the priority of task
    5. Switch for the status of task

    -> editing the task
    1. existing data is prefilled in the form
    2. edit the data in the form and update by the update button
    3. task can also be deleted by the delete button

--> Navigation is managed properly for the applicaitons
--> BackStack is also managed
--> press back again to exit is also implemented

    Workmanager is also implemented to give notifications about the remaining high priority task and the missed deadline tasks
