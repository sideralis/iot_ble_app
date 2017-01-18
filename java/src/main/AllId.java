package main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AllId {
	Date temps;
	Map<String, ToId> fromID;
	
	public AllId() {
		this.temps = null;
		this.fromID = new HashMap<>();
	}

	public void output() {
		System.out.println("{");
		for (Map.Entry<String, ToId> fromid : this.fromID.entrySet()) {
			System.out.println("    \"id\": " + fromid.getKey() + ",");
			System.out.println("    \"around\": [");
			for (Map.Entry<String, Integer[]> rssi : fromid.getValue().rssi.entrySet()) {
				System.out.println("        {");
				System.out.println("        \"to\": " + rssi.getKey() + ",");
				System.out.println("        \"rssi\": " + rssi.getValue()[0]/rssi.getValue()[1]);
				System.out.println("        },");
			}
			System.out.println("    ],");
		}
		System.out.println("}");
	}
	
	public void parse(String fromid, String toid, int rssi, Date temps) {
		// Special case: first measurement received
		if (this.temps == null)
			this.temps = temps;
		
		// Calculate number of second elapsed since beginning of this 5 seconds slice
		long offset = (temps.getTime() - this.temps.getTime()) / 1000;
				
		// Did we move to next slice?
		if (offset >=5) {
			// Yes, print result
			output();
			// and restart from 0
			this.temps = temps;
			this.fromID = new HashMap<>();
		}
		
		// Now add the new measurement
		if (this.fromID.containsKey(fromid)) {
			this.fromID.get(fromid).addTo(toid, rssi);
		} else {
			this.fromID.put(fromid, new ToId(toid, rssi));
		}
	}
}
