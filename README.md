
![image](https://github.com/user-attachments/assets/21739839-6c31-4e8f-ad37-776d31efb07b)

# Carousell News
[Carousel News](https://drive.google.com/file/d/1ZIhvDS3E7WRdj_KJn52IFC0ecSZwHiP-/view?usp=sharing) is an Android application that allows you to view information about carousell news.


### Overview
This application is developed using various technologies and libraries, including Room, Retrofit, Lottie, Shimmer and Dagger Hilt.

### Source API
The data for this application is sourced from a [this API](https://storage.googleapis.com/carousell-interview-assets/android/carousell_news.json) 

### Core Technologies
- [Room](https://developer.android.com/jetpack/androidx/releases/room) for local data storage.
- [Retrofit](https://square.github.io/retrofit/) for server communication.
- [Dagger Hilt](https://dagger.dev/hilt/) for dependency injection.
- [Lotties](https://lottiefiles.com/) for animation.
- [Tourbine](https://github.com/cashapp/turbine) for unit testing.
- [Chucker](https://github.com/ChuckerTeam/chucker) for see traffic log data.

### About Architecture
![image](https://github.com/user-attachments/assets/c6cb8627-513d-484e-9e4f-b4ed61c9cec2)
![image](https://github.com/user-attachments/assets/837328b8-d7f5-4f74-856f-c0369d6bdc47)
![image](https://github.com/user-attachments/assets/5114885c-e306-4a7a-9f1a-52503c8632c7)

### Unit Testing
Using turbine for unit testing to ensure all news successfuly load
<br>
<img width="276" alt="image" src="https://github.com/user-attachments/assets/f0cb86e7-c216-45ac-b10d-86ffd67606e5">

### Ui Testing
Using Espresso and idling resources, with scenario
- Ensure all news successfuly loaded
- Sort with rank & populer
- Refresh news

https://github.com/user-attachments/assets/3c7871f0-1ad0-47b8-bd69-0bd98677de34


### How to Use
1. Clone this repository to your device.
2. Open the project using Android Studio.
3. Run the application on an emulator or physical device.


   
### Contribution
We welcome contributions from the developer community. If you would like to contribute to this project, please open a new issue or submit a pull request.
