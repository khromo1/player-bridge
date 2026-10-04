package streaming.player;

public class BluetoothOutput implements AudioOutput {
    @Override
    public void output(String audioData) {
        System.out.println("Streaming via Bluetooth: " + audioData);

    }
}
