package SMPTraveller.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Teleports {


    public class Coordinates {

        private double x;
        private double y;
        private double z;
        private float yaw;
        private float pitch;

        private boolean isSet;

        private String world;

        protected Coordinates() {

            this.x = 0;
            this.y = 0;
            this.z = 0;

            this.yaw = 0;
            this.pitch = 0;

            this.isSet = false;

            this.world = null;

        }


        public Coordinates(double x, double y, double z, float yaw, float pitch, String world) {

            if (world == null || world.isEmpty()) {
                Bukkit.getLogger().severe("Invalid world - Cannot set coordinates");
                this.isSet = false;
                return;
            }

            this.world = world;

            this.x = x;
            this.y = y;
            this.z = z;

            this.yaw = yaw;
            this.pitch = pitch;

            this.isSet = true;

        }

        public void setCords(double x, double y, double z, float yaw, float pitch, String world) {

            if (world == null || world.isEmpty()) {
                Bukkit.getLogger().severe("Invalid dimension - Cannot set coordinates");
                this.isSet = false;
                return;
            }

            this.world = world;


            this.x = x;
            this.y = y;
            this.z = z;

            this.yaw = yaw;
            this.pitch = pitch;

            this.isSet = true;
        }


        public void setDimension(String world) {

            if (world == null || world.isEmpty()) {
                Bukkit.getLogger().severe("Invalid dimension - Cannot set coordinates");
                this.isSet = false;
                return;
            }

            this.world = world;

        }

        public double[] getCoords() {

            return new double[]{this.x, this.y, this.z};

        }

        public String getWorld() {

            return this.world;

        }

        public float[] getRotation() {

            return new float[]{this.yaw, this.pitch};

        }

        public boolean isSet() {
            return this.isSet;
        }

    }

    protected Map<String, Coordinates> coords = new HashMap<String, Coordinates>();
    public static final int countdown = 3;
    private final JavaPlugin plugin;

    public Teleports(JavaPlugin plugin) {

        this.plugin = plugin;
        coords.put("end", new Coordinates());
        coords.put("spawn", new Coordinates(0, 200, 0, 0, 0, Bukkit.getWorlds().get(0).getName()));
        coords.put("shop", new Coordinates());

    }


    public void setCoords(String key, double x, double y, double z, float yaw, float pitch, String world) {

        if (key == null || key.isEmpty()) {
            Bukkit.getLogger().severe("Invalid key - Cannot set coordinates");
            return;
        }

        if (world == null || world.isEmpty()) {
            Bukkit.getLogger().severe("Invalid dimension - Cannot set coordinates");
            return;
        }

        Coordinates coords = new Coordinates(x, y, z, yaw, pitch, world);

        this.coords.put(key, coords);

        DataManager.save(this);

    }


    public void teleport(Player player, String key) {

        if (key == null || key.isEmpty()) {
            Bukkit.getLogger().severe("Invalid key - Cannot teleport player");
            return;
        }

        if (!this.coords.containsKey(key)) {
            Bukkit.getLogger().severe("Invalid key - Cannot teleport player");
            return;
        }

        Coordinates coords = this.coords.get(key);

        if (!coords.isSet()) {
            Bukkit.getLogger().severe("Invalid coordinates - Cannot teleport player");
            return;
        }

        World world = Bukkit.getWorld(coords.getWorld());

        if (world == null) {
            Bukkit.getLogger().severe("Invalid world - Cannot teleport player");
            return;
        }

        Location loc = new Location(world, coords.getCoords()[0], coords.getCoords()[1], coords.getCoords()[2],
                coords.getRotation()[0], coords.getRotation()[1]);


        if (loc.getBlock().getType().isSolid()) {
            player.sendMessage("§cTeleport location is unsafe!");
            return;
        }

        for (int i = 0; i < countdown; i++) {
            final int timeLeft = countdown - i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.sendTitle("Teleporting to §6" + key + "§f in: " + timeLeft, "", 0, 20, 0);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }, i * 20L);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.teleport(loc);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        }, countdown * 20L);
    }

    public boolean exists(String key) {

        if (key == null || key.isEmpty()) {
            Bukkit.getLogger().severe("Invalid key - Cannot check if exists");
            return false;
        }

        return this.coords.containsKey(key) && this.coords.get(key).isSet();

    }

    public void reload() {
        DataManager.load(this);
    }

    public boolean remove(String key) {

        if (key == null || key.isEmpty()) {
            Bukkit.getLogger().severe("Invalid key - Cannot remove location");
            return false;
        }

        if (!this.coords.containsKey(key)) {
            Bukkit.getLogger().severe("Invalid key - Cannot remove location");
            return false;
        }

        this.coords.remove(key);
        DataManager.delete(key);
        return true;
    }

    public void save() {
        DataManager.save(this);
    }

    public void load() {
        DataManager.load(this);
    }

    public String[] getKeys() {
        return this.coords.keySet().toArray(new String[0]);
    }

}
