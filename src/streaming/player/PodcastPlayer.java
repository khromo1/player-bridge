package streaming.player;

public class PodcastPlayer extends MediaPlayer {
    private int lastPositionSeconds = 0;
    public PodcastPlayer (AudioOutput output) {
        super(output);
    }
    @Override
    public void play(String title) {
        output.output("Resuming podcast episode: " + title + " from " + lastPositionSeconds + "s");
    }
    public void pauseAt(int seconds) {
    this.lastPositionSeconds = seconds;
    }
}