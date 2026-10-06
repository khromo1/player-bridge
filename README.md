# Streaming Player Bridge

Software Design Patterns — Assignment 3 (Bridge pattern), Astana IT University.

## What this is

Still the same music streaming theme as the previous two assignments, but a different problem this time: a media player needs to work the same way regardless of where the sound actually goes (Bluetooth speaker, wired speaker, whatever gets added later), and at the same time there are different kinds of players (a regular track player, a podcast player that needs to remember playback position) that shouldn't have to know anything about audio output at all.

Without Bridge, supporting every combination of player type × output type would mean a new class per pair — `BluetoothStandardPlayer`, `SpeakerStandardPlayer`, `BluetoothPodcastPlayer`, `SpeakerPodcastPlayer`, and it gets worse with every new output device or player type. Bridge splits this into two independent hierarchies connected through composition instead of inheritance, so either side can grow without touching the other.

## Structure

```
src/streaming/player/
  AudioOutput.java      - Implementor
  BluetoothOutput.java   - Concrete Implementor
  SpeakerOutput.java     - Concrete Implementor
  MediaPlayer.java       - Abstraction
  StandardPlayer.java    - Refined Abstraction
  PodcastPlayer.java     - Refined Abstraction
  Client.java             - demonstrates both sides, and switching at runtime
```

## How the bridge works

`MediaPlayer` holds a reference to an `AudioOutput` — that reference is the actual "bridge." `MediaPlayer` never knows whether sound ends up going out over Bluetooth or through a speaker; it just calls `output.output(...)` and lets the concrete implementor deal with it.

```java
MediaPlayer player = new StandardPlayer(new BluetoothOutput());
player.play("Blinding Lights");

// same player object, different implementation — no new class needed
player.setOutput(new SpeakerOutput());
player.play("Blinding Lights");
```

`PodcastPlayer` is the second Refined Abstraction — it adds its own behavior (remembering where playback stopped) on top of the same bridge, without `AudioOutput` needing to know anything about podcasts.

## Running it

```
javac src/streaming/player/*.java -d out
java -cp out streaming.player.Client
```

Expected output:
```
Streaming via Bluetooth: Now playing: Blinding Lights
Playing through speaker: Now playing: Blinding Lights
Streaming via Bluetooth: Resuming podcast episode: Design Patterns 101 from 1230s
Playing through speaker: Resuming podcast episode: Design Patterns 101 from 1230s
```

## Clean code choices

**1. Composition over inheritance.**
```java
// not this — would need a new class for every player type × output type combo
// class BluetoothStandardPlayer extends StandardPlayer { ... }

// this instead — MediaPlayer just holds a reference
protected AudioOutput output;
```
This is the whole point of Bridge: adding a third output type later (say, `WiredOutput`) means writing one new class, not multiplying every existing player type by it.

**2. Abstraction depends only on the Implementor interface, never a concrete class.**
`MediaPlayer`'s field is typed `AudioOutput`, not `BluetoothOutput` — so `StandardPlayer` and `PodcastPlayer` work with literally any current or future implementor without changing a line.

**3. Single-purpose classes on both sides.**
Each `AudioOutput` implementation only knows how to push audio out through one channel. Each `MediaPlayer` subclass only knows its own playback behavior. Neither side is doing the other's job.

**4. No leaking implementation details to the client.**
`Client` calls `player.play(title)` — it never touches `AudioOutput` internals directly beyond picking which one to pass in. The output formatting, connection handling, etc. all stay inside the Concrete Implementor.

**5. Runtime flexibility without reconstruction.**
```java
player.setOutput(new SpeakerOutput());
```
Switching the implementor doesn't mean throwing away the `MediaPlayer` object and building a new one — the abstraction and its state (e.g. a podcast's saved position) stay intact while only the output channel changes.

## Notes

Factory Method / Abstract Factory (previous assignment) are about *creating* objects. Bridge is about *structure* — keeping two independently-changing hierarchies (player type, output type) decoupled through composition instead of collapsing them into one inheritance tree.
