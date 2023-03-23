package com.websocket;


import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class LoesungEmpfaenger {
    @Autowired
    private SimpMessagingTemplate broker;

    @Autowired
    public LoesungEmpfaenger(final SimpMessagingTemplate broker) {
        this.broker = broker;
    }

    @PostConstruct
    public void run() {
        String bbroker2 = "tcp://localhost:1883";
        String topic = "Loesung";
        String clientid2 = "sub_client2";
        int qos = 0;

        try {
            MqttClient client2 = new MqttClient(bbroker2, clientid2, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            client2.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) {
                    broker.convertAndSend("/topic/greetings",
                            new Greeting("Loesung: " + new String(message.getPayload())));
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });
            client2.connect(options);
            client2.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}