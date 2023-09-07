package me.kardoskevin07.cuffedtojail.models;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("JailSign")
public class JailSign implements ConfigurationSerializable {
    private final Location location;
    private final String  jailName;

    public JailSign(Location location, String jailName) {
        this.location = location;
        this.jailName = jailName;
    }

    public String getJailName() {
        return jailName;
    }
    public Location getLocation() {
        return location;
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap result = new LinkedHashMap();
        result.put("location", location);
        result.put("jailName", jailName);

        return result;
    }
    public static JailSign deserialize(Map<String,Object> args) {
        Location location = null;
        String jailName = null;

        if(args.containsKey("location")) {
            location = (Location)(args.get("location"));
        }
        if(args.containsKey("jailName")) {
            jailName = (String)(args.get("jailName"));
        }

        return new JailSign(location, jailName);
    }
}
