# Screenshots

This directory contains screenshots of the Android Countries List App.

## Screenshot Files

- `portrait_screenshot.png` - App running in portrait mode
- `landscape_screenshot.png` - App running in landscape mode

## How to Take Screenshots

1. **Using Android Studio:**
   - Run the app in the emulator
   - Use the camera icon in the emulator toolbar
   - Save the screenshot to this directory

2. **Using ADB:**
   ```bash
   adb shell screencap -p /sdcard/screenshot.png
   adb pull /sdcard/screenshot.png screenshots/
   ```

3. **Using Device:**
   - Take screenshots on physical device
   - Transfer to this directory

## Screenshot Requirements

- **Resolution**: Minimum 1080x1920 (Full HD)
- **Format**: PNG format
- **Content**: Show the main countries list
- **Orientations**: Both portrait and landscape
- **Quality**: High quality, clear text

## Current Status

Screenshots will be added after testing the app in both orientations.
