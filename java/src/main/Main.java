package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

public class Main implements MqttCallback {

	public Main() {
	}
	
	public static void main(String[] args) {
		Main mqtt = new Main();
		//mqtt.subscribe();
		mqtt.readFile("rssi_data.txt");
	}
	
	public void readFile(String nameOfFile) {
        List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(nameOfFile));
	        for (String line : lines) {
	        	decodeMessage(line);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void subscribe() {
		MqttClient client;
		try {
			client = new MqttClient("tcp://iot.eclipse.org", "donotmatter");
			client.setCallback(this);
			client.connect();
			client.subscribe("campusid/edison/rssi");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void decodeMessage(String msg) {
		//System.out.println("Message = " + msg );
		JSONObject obj = new JSONObject(msg);
		String from = obj.getString("from");
		String to = obj.getString("to");
		String dateAsString = obj.getString("date");
		int rssi = obj.getInt("rssi");

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		Date date;
		try {
			date = format.parse(dateAsString);
			___.parse(from, to, rssi, date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) {
		String msg = new String(message.getPayload());
		decodeMessage(msg);
	}

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}
}
