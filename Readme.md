Author: Jie Wen

This mobile application uses MVVM as the software architecture, view, view model and model.

I use RxJava2 + Retrofit2 + OkHttp3 + Moshi adapter to develop this project.

I create three packages "view", "viewmodel" and "model" for each journey or story, that makes the developers easy to read by features. "view" package contains all UI related codes, "viewmodel"
contains all the business logic codes (no Android codes in viewmodel part), "model" contains data class and api call service.

I choose RxBus to handle communication between view and viewmodel. Also I use "Bundle" to send from Activity to Fragment, and use Observer(interface) to handle communication from Fragment to
activity.

For future feature add and enhancement, I created Abstract base activity class, fragment class and viewmodel for all activities, fragments and view models. Each new sub class of Activity, Fragment
and ViewModel can extend these super classes. I put most of the basic thing needed to do in the abstract class then other developers can mainly focus on new feature development and enhancement.
I will destroy all the disposable when view is destroyed to avoid memory leak. I choose Picasso to handle the image loading to prevent out of memory error. I use RxJava to prevent ANR.

I create some base error handler to handle error and developers can add new error handling in the ErrorHandling class.

In ApiFactory.kt, I create the basic Api error interceptor, header interceptor, cookies manager and certificate pinner for future use, and also I make a comment that we can add authentication interceptor in OkHttpBuilder
if we need authentication for future security enhancement.
