Build tools & versions used
• Android Gradle Plugin: com.android.application
• Kotlin Plugin: org.jetbrains.kotlin.android
• Compile SDK: 33
• Min SDK: 24
• Target SDK: 33
• Version Code: 1
• Version Name: "1.0"
• Java Version: 1.8
• Data Binding: Enabled
Steps to run the app

1. Clone the repository.
2. Open the project in Android Studio.
3. Ensure you have the correct SDK versions installed (Compile SDK 33).
4. Build the project by clicking on Build > Rebuild Project.
5. Run the app on an emulator or a physical device with Android version 24 or higher.

What areas of the app did you focus on?
   I focused on implementing the main activity which displays a list of employees, including the
   following:
   • Setting up Retrofit for network requests.
   • Creating a repository pattern to fetch data.
   • Implementing use cases to handle business logic.
   • Using ViewModel and LiveData for managing UI-related data.
   • Implementing RecyclerView with a custom adapter for displaying the list.
   • Adding pull-to-refresh functionality using SwipeRefreshLayout.

What was the reason for your focus? What problems were you trying to solve?
The primary focus was to create a scalable and maintainable architecture that separates concerns.
This involves:
• Ensuring that the network layer is isolated from the rest of the app.
• Handling business logic separately from UI logic.
• Providing a responsive and user-friendly UI that handles data loading efficiently.
• Implementing error handling and empty state views to enhance user experience.

How long did you spend on this project?
The project took approximately 5-6 hours to complete. This includes setting up the project
structure, implementing the necessary classes and functions, testing, and debugging.

Did you make any trade-offs for this project? What would you have done differently with more time?
Yes, there were a few trade-offs made due to time constraints:
• Error handling: Implemented basic error handling, but more detailed error messages and recovery
options could be added.
• Unit tests: Basic unit tests were not included but should be added for the ViewModel and use case
layers.
• UI enhancements: With more time, additional UI/UX improvements and animations could be
implemented.
• Caching: Implementing data caching to handle offline scenarios was skipped due to time
constraints.

What do you think is the weakest part of your project?
The weakest part of the project is the lack of comprehensive unit tests and error handling. While
basic functionality is covered, more rigorous testing and better error management would improve the
robustness of the application.

Did you copy any code or dependencies? Please make sure to attribute them here!
No code was directly copied, but the project uses several dependencies:
• Retrofit and OkHttp for network requests.
• Glide for image loading.
• Kotlin Coroutines for asynchronous programming.
• Android Jetpack components like ViewModel, LiveData, and SwipeRefreshLayout.

Is there any other information you’d like us to know?
This project demonstrates a clean architecture approach with a clear separation of concerns. The
implementation is modular, making it easy to extend and maintain. Future improvements could include
more robust error handling, data caching strategies, and additional unit and UI tests to ensure
high-quality code.

