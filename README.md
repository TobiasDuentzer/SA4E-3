# SA4E-3

MessagingStompWebsocketApplication startet das Programm und den Websocket.
Unter http://localhost:8080/ ist der Websocket zu finden.

Den MQTT Server habe ich local mittels mosquitto gehostet (broker = "tcp://localhost:1883").
Dieser ist in LoesungEmpfangen und RaetselEmpfangen beliebig zu ändern.

Um alle MQTT Nachrichten der Topics "Zahlenraetsel" und "Loesungen" zu sehen muss auf "Connect" geclickt werden.

Das ist das erste mal, dass ich Docker benutze. Ich hoffe ich habe das richtig verstanden.

Docker und Terminal starten. Im Terminal zu "websocket" navigieren. Dann:

docker build -t websocket-docker:latest .

docker run -p 8080:8080 websocket-docker

Zu beachten ist, dass ein localer mosquitto server vorhanden sein muss über den die MQTT messages versendet werden.
