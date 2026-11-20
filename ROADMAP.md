# Chronicity roadmap (ideas to hold on to)

## Near term
- Audio-clock scheduling (Web Audio look-ahead) so fast rhythms are sample-accurate.
- "From system" sound option (Android picker plugin; show chosen name on a line under the dropdown).
- Free and premium are SEPARATE apps/repos from here on. Free stays simple and elegant (current feature set, no upsell clutter). Premium is its own project and app ID (e.g. app.chronicity.premium).
- Signed release builds + permanent signing key; versionCode already comes from the CI run number.
- iOS: free = web app via Pages (done). Paid Apple Developer account later for TestFlight/App Store; background audio needs native work.

- Per-timer offset/delay (ms): needed to line up lanes and to compensate latency when adding a MIDI synth or Bluetooth audio.

## Premium ideas (separate app)
- Custom synth chords played at specific intervals.
- Groups of alarms in Time Master, with color coding and color effects in the visualizer.
- Sound sets: drums, synth, clocks, guitar, piano. Clocks/guitar/piano need single notes + chords (columns); pick exact notes and build chord progressions.
- Special actions / rules: "after X rounds" -> stop, or start another timer/alarm (e.g. after Y rounds start Alarm 3), jump to a segment, change sound, toggle metronome. A small trigger -> action rule list per segment or alarm, which enables song-like structures (verse x4 -> chorus).
- Polyrhythm tools: BPM entry, ratios (3:4), step grid per lane; wheel view doubles as a polyrhythm diagram.
- Separate premium app ID (e.g. app.chronicity.timer.pro).
