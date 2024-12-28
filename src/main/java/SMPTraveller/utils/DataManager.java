package SMPTraveller.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class DataManager {


    private static final String locationsFileName = "SMPTraveller.yml";
    private static File locationsFile;
    private static FileConfiguration locations;

    public static void setup() {

        locationsFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("SMPTraveller")).getDataFolder(), locationsFileName);

        if (!locationsFile.exists()) {
            try {
                locationsFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Failed to create locations file: " + e.getMessage());
            }
        }

        locations = YamlConfiguration.loadConfiguration(locationsFile);

    }

    private static void saveToFile() {
        try {
            locations.save(locationsFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to save locations data: " + e.getMessage());
        }
    }


    public static void save(Teleports teleports) {

        for (Map.Entry<String, Teleports.Coordinates> entry : teleports.coords.entrySet()) {

            if (entry.getValue() == null || entry.getKey() == null) {
                continue;
            }

            if (!entry.getValue().isSet()) {
                continue;
            }

            String key = entry.getKey();
            Teleports.Coordinates coords = entry.getValue();

            double[] xyz = coords.getCoords();
            float[] rotation = coords.getRotation();


            locations.set("locations." + key + ".world", coords.getWorld());
            locations.set("locations." + key + ".isSet", coords.isSet());

            locations.set("locations." + key + ".x", xyz[0]);
            locations.set("locations." + key + ".y", xyz[1]);
            locations.set("locations." + key + ".z", xyz[2]);

            locations.set("locations." + key + ".yaw", rotation[0]);
            locations.set("locations." + key + ".pitch", rotation[1]);

        }

        saveToFile();

    }

    public static void load(Teleports teleports) {

        if (!locations.contains("locations")) {
            locations.createSection("locations");
            saveToFile();
            return;
        }

        for (String key : locations.getConfigurationSection("locations").getKeys(false)) {

            if (!locations.getBoolean("locations." + key + ".isSet")) {
                continue;
            }


            double x = locations.getDouble("locations." + key + ".x");
            double y = locations.getDouble("locations." + key + ".y");
            double z = locations.getDouble("locations." + key + ".z");

            float yaw = (float) locations.getDouble("locations." + key + ".yaw", 0.0);
            float pitch = (float) locations.getDouble("locations." + key + ".pitch", 0.0);

            String dimension = locations.getString("locations." + key + ".world");

            Teleports.Coordinates coords = teleports.new Coordinates(x, y, z, yaw, pitch, dimension);


            teleports.coords.put(key, coords);
        }
    }

    public static void delete(String key) {

        if (!locations.contains("locations." + key)) {
            return;
        }

        locations.set("locations." + key, null);
        saveToFile();

        Bukkit.getLogger().info("Location " + key + " deleted");
    }
}

