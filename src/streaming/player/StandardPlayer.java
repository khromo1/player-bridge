package streaming.player;

public class StandardPlayer extends MediaPlayer {
    public StandardPlayer(AudioOutput output) {
        super(output);
    }
    @Override
    public void play(String title) {
        output.output("Now Playing: " + title);
    }
}
