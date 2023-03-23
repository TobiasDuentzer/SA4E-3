package com.websocket;


import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RaetselEmpfaenger {
    @Autowired
    private SimpMessagingTemplate broker;

    @Autowired
    public RaetselEmpfaenger(final SimpMessagingTemplate broker) {
        this.broker = broker;
    }

    @PostConstruct
    public void run() {
        String bbroker = "tcp://localhost:1883";
        String topic = "Zahlenraetsel";
        String clientid = "sub_client";
        int qos = 0;

        try {
            MqttClient client = new MqttClient(bbroker, clientid, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) {
                    broker.convertAndSend("/topic/greetings",
                            new Greeting("Raetsel: " + new String(message.getPayload())));
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });
            client.connect(options);
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}