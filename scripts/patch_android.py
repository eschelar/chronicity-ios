"""Run after `npx cap add android`: installs native code, manifest entries, version numbers."""
import json, os, re, shutil
cfg = json.load(open('capacitor.config.json'))
app_id = cfg['appId']
pkg = json.load(open('package.json'))
ver = pkg['version'].split('-')[0]
run = os.environ.get('GITHUB_RUN_NUMBER', '1')

jdir = 'android/app/src/main/java/' + app_id.replace('.', '/')
os.makedirs(jdir, exist_ok=True)
for f in ['MainActivity.java', 'ChronicityPlugin.java', 'TimerService.java']:
    t = open('native/android/' + f).read().replace('app.chronicity.timer', app_id)
    open(jdir + '/' + f, 'w').write(t)

mp = 'android/app/src/main/AndroidManifest.xml'
m = open(mp).read()
if 'FOREGROUND_SERVICE_MEDIA_PLAYBACK' not in m:
    perms = ('    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />\n'
             '    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />\n'
             '    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />\n'
             '    <uses-permission android:name="android.permission.WAKE_LOCK" />\n')
    m = m.replace('<application', perms + '    <application', 1)
    m = m.replace('</application>', '    <service android:name=".TimerService" android:exported="false" android:foregroundServiceType="mediaPlayback" />\n    </application>', 1)
    open(mp, 'w').write(m)

gp = 'android/app/build.gradle'
g = open(gp).read()
g2 = re.sub(r'versionCode\s+\d+', 'versionCode ' + run, g, count=1)
g2 = re.sub(r'versionName\s+"[^"]*"', 'versionName "' + ver + '"', g2, count=1)
if g2 == g: print('WARNING: version lines not found in build.gradle')
open(gp, 'w').write(g2)
print('patched: app id', app_id, 'version', ver, 'build', run)
