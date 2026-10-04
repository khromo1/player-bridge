package streaming.player;

public class SpeakerOutput implements AudioOutput {
    @Override
    public void output(String audioData) {
        System.out.println("Playing via speaker:" + audioData);
    }
}
