package main;

import java.util.HashMap;
import java.util.Map;

public class ToId {
	Map<String, Integer[]> rssi;

	public ToId(String toID, int rssi) {
		this.rssi = new HashMap<>();
		this.rssi.put(toID, new Integer[] {rssi, 1});
	}
	
	public void addTo(String toID, int rssi) {
		if (this.rssi.containsKey(toID)) {
			Integer[] newrssi = this.rssi.get(toID);
			newrssi[0] += rssi;			// Accumulate rssi to do a mean later on
			newrssi[1] += 1;			// Store number of accumulation
			this.rssi.put(toID, newrssi);
		} else {
			this.rssi.put(toID, new Integer[] {rssi, 1});
		}
	}
}
