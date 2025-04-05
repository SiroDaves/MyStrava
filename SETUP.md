### **SETUP.md** (Android-Specific)

# MyStrava Android Setup Guide

## 1. Clone the Repository
```bash
git clone https://github.com/SiroDaves/MyStrava.git
```

## 2. Configure Strava API Credentials
1. Register your app on the [Strava API Dashboard](https://www.strava.com/settings/api).
2. On your `local.properties` file in the root project directory add :
   ```
    strava.client.id="your_client_id"
    strava.client.secret="your_client_secret"
   ```
   Please not that this file is usually gitignored

## 3. Open Project in Android Studio
- Launch Android Studio and select **File > Open** to load the project.
- Sync Gradle dependencies (may take a few minutes).

## 4. Build and Run
1. Connect an Android device or start an emulator.
2. Click **Run** in Android Studio (or press `Shift+F10`).

## 5. Enable Strava OAuth
- The app should redirect you to Strava’s login page for authentication on first launch.

## Troubleshooting
- **Build Errors**: Ensure `secrets.properties` is correctly formatted.
- **API Issues**: Verify your Strava API quota and credentials.
```
