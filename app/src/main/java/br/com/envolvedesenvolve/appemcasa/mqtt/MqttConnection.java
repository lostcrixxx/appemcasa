package br.com.envolvedesenvolve.appemcasa.mqtt;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import br.com.envolvedesenvolve.appemcasa.ui.home.HomeFragment;

public class MqttConnection {

    private static final String TAG = MqttConnection.class.getName();
    private static final String serverUri = "tcp://broker.hivemq.com:1883";
    private static final String PUB_TOPIC = "appemcasa/test";

    MqttAndroidClient mqttAndroidClient;

    public void setConnection(Context context) {
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, MqttClient.generateClientId());
        mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "Connection was lost!");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                Log.e(TAG, "Message Arrived!: " + topic + ": " + new String(message.getPayload()));

                HomeFragment.getInstance().updateText(new String(message.getPayload()));


//                Toast.makeText(context, "Teste " + new String(message.getPayload()), Toast.LENGTH_LONG).show();

//                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//                vibrator.vibrate(2000);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.e(TAG, "Delivery Complete!");
            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setKeepAliveInterval(60);//seconds
        mqttConnectOptions.setCleanSession(true);
//        mqttConnectOptions.setAutomaticReconnect(true);

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e(TAG, "Connection Success!");
                    try {
                        mqttAndroidClient.subscribe(PUB_TOPIC, 2);
                    } catch (MqttException ex) {
                        Log.e(TAG, "ERRO mqttAndroidClient subscribe");
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Toast.makeText(context, "ERRO Sem conexão !", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Connection Failure!");
                    Log.e(TAG, "throwable: " + exception.toString());
                    exception.printStackTrace();
                }
            });
        } catch (MqttException ex) {
            Log.e(TAG, ex.toString());
        }
    }

    public void publishMessage(String payload) {
        try {
            if (!mqttAndroidClient.isConnected()) {
                mqttAndroidClient.connect();
            }

            MqttMessage message = new MqttMessage();
            message.setPayload(payload.getBytes());
            message.setQos(2);
            mqttAndroidClient.publish(PUB_TOPIC, message,null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "publish succeed!") ;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "publish failed!") ;
                }
            });
        } catch (MqttException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }
}
