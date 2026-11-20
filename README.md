# Chronicity (alpha)

Time Master (multi-interval alarms) + Tabata (rounds, metronome, ring display).

## Easiest: build the APK in the cloud (no Android tools needed)
1. Create a free GitHub account and a new empty repository.
2. Upload everything in this folder (keep the `.github` folder).
3. Open the repo's **Actions** tab, choose **Build alpha APK**, click **Run workflow**.
4. When it finishes (5-10 min), open the run and download **chronicity-alpha-apk**.
5. Unzip, send `app-debug.apk` to your phone, tap it, allow "install unknown apps" if asked.

## Quick test without any build: install as a PWA
Host the `www` folder on any HTTPS site (GitHub Pages, Netlify). Open it in Chrome on Android, menu > **Install app**.

## Local build with Android Studio
`npm install`, then `npm run android:setup`, then `npm run android:open` and press Run.

## Notes
- App ID `app.chronicity.timer` is permanent once on Google Play. Change it in `capacitor.config.json` before the first upload if you want something else.
- Alpha limits: screen stays on while running, but timers/audio pause if the screen locks or the app is backgrounded. A native foreground service is the planned fix.
- Play Store later: signed release bundle (.aab), current target-API rules, Play Console account. Premium = separate app ID (e.g. `app.chronicity.timer.pro`).

## iPhone/iPad (free): share as a web app
No Apple developer account needed.
1. Repo **Settings > Pages > Source: GitHub Actions** (free accounts need a public repo for Pages; for a private repo drag the `www` folder onto app.netlify.com/drop instead).
2. Push to `main` (or run **Deploy web app** in Actions). Your link will be `https://<user>.github.io/<repo>/`.
3. Friends open the link in **Safari**, tap Share > **Add to Home Screen**, and launch it from the new icon.
iOS limits: the screen must stay on (the app requests this), and timers/sound stop if the phone locks or Chronicity is closed. A true App Store/TestFlight build needs a paid Apple Developer account ($99/yr).

## Native Android pieces (added v0.1.1)
`native/android/` holds a foreground service (keeps timers alive with the screen off) and a small plugin (battery-settings links, notification permission). `scripts/patch_android.py` installs them after `cap add android`, and sets versionName/versionCode.

## Release flow
Create a tag like `v0.1.1` (GitHub > Releases > Draft new release > new tag). The workflow builds and attaches the APK to that release; share the release page with friends.

## Playtest quick start
- **iPhone testers (fastest, today):** drag the `www` folder onto https://app.netlify.com/drop, then send friends `<your-link>/playtest.html`. They open it in Safari and follow the steps.
- **Android testers:** push this repo to GitHub, tag a release (e.g. `v0.1.1`) and send `<pages-link>/playtest.html` or the Releases page. The repo must be public for friends to download from Releases; otherwise send them the APK file directly.
