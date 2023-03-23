package MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class publisher {
    public static void main(String[] args) throws IOException {
        String broker = "tcp://localhost:1883";
        String topic = "Loesung";   //Zahlenraetsel     Loesung
        String clientid = "pub_client";
        //String content = "Hello MQTT";
        byte[] bytes = Files.readAllBytes(Path.of("input.txt"));
        int qos = 0;

    try {
        MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        client.connect(options);

        MqttMessage message = new MqttMessage(bytes);
        message.setQos(qos);
        client.publish(topic, message);

        client.disconnect();
        client.close();
    }catch (MqttException e) {
    throw new RuntimeException(e);
}
    }
}