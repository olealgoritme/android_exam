### Android exam 2020 report by Ole Algoritme ###

## Github: https://github.com/olealgoritme/android_exam

## Libraries ##
androidx.appcompat:appcompat:1.1.0
com.google.android.material:material:1.1.0
androidx.recyclerview:recyclerview:1.1.0
androidx.constraintlayout:constraintlayout:2.0.0-beta4
androidx.recyclerview:recyclerview-selection:1.1.0-rc01
androidx.core:core-ktx:1.2.0
com.squareup.okhttp3:okhttp:4.4.0
com.squareup.okio:okio:2.4.3
com.squareup.picasso:picasso:2.71828
com.squareup.retrofit2:retrofit:2.7.2
com.squareup.retrofit2:converter-gson:2.7.2
com.google.code.gson:gson:2.8.6
com.google.android.gms:play-services-maps:17.0.0
com.google.android.libraries.places:places:2.2.0
com.google.android.material:material:1.2.0-alpha05
com.github.wangjiegulu:rfab:2.0.0
com.airbnb.android:lottie:3.4.0

## Library choice explanation ##
-- Picasso - for it's easy of use and built-in caching function with LruCache (app is set to 250MB image caching)
-- Retrofit - for easy of use with API endpoints
-- Google Maps - quickest to implement. Thought about implementing mapbox, since that's what Noforeignland was using, but went back to GMaps
-- Material - for some custom UI designed elements
-- Lottie - for easy animation implementation


## UI elements ##
-- ConstraintLayout, FrameLayout, LinearLayout
-- Neuphormic UI elements (Copyright https://github.com/fornewid/neumorphism/)
-- MaterialCards
-- ProgressBar (Lottie)
-- Splash Animation (Lottie)
-- Activity View transition animation (View1@Activity -> View1@2ndActivity)


## Network calls ##
-- Threads (Single thread Thread()s)
-- Threadpool (AsyncTask)

## Database ##
-- SQLiteOpenHelper extension - DBInstance.kt
-- Using transactions for large lists of objects


## Helpers ##
-- IconDetails.kt - Helper to get description based on icon type "nfl_*"
-- ImageLoader.kt - Picasso singleton instance
-- DBInstance.kt - SQLiteOpenHelper extension with singleton, to avoid multiple db connections
-- App.kt - Application extensions with access to static App context




Afternote:

The API creator of noforeignland.com has some errors in the API, specifically at places/ endpoint, where geometry.locations has longitude
stored as the first element, in stead of latitude - which would have been the most logical. I also found a Google Maps key when glancing at the source code. Tried to contact the owner about it, but to no awail - direct contact information is well hidden.

Learning kotlin was a bit of a challenege at first, but after a few hours, it wasn't that bad.
Without the Android Studio IDE and it's suggestions it would have been a lot of Googling.

Kotlin wise, I would have gone with room for database and coroutines for network calls, but both of them seem to be alternatives which are very different than the standard Android ones, so I chose not to use them.

On a personal note, I don't understand why Google is endorsing this new kind of JS/TS programming language. It was a cool experience nevertheless, but not really my cup of tea.


I have used zoom on several occasions to teach others how to use certain techniques. Sharing is caring.
Thanks for a good exam, and good luck guys.


Sincerely,
Ole Algoritme

