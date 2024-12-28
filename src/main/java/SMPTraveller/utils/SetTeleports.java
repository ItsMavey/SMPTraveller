package SMPTraveller.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTeleports {

    private final Teleports teleports;
    private final String[] args;

    public SetTeleports(Teleports teleports, String[] args) {
        this.teleports = teleports;
        this.args = args;
    }


    public boolean set(CommandSender sender) {

        String location = args[1];

        int length = args.length;

        switch (length) {
            case 2:
                return setNoCoords(sender, location);
            case 4:
                return setCoords(sender, args[1], args[2], args[3]);
            case 5:
                return setWorldedCoords(sender, args[1], args[2], args[3], args[4]);
            case 7:
                return setDirectedCoords(sender, args[1], args[2], args[3], args[4], args[5], args[6]);
            case 8:
                return setWorldedDirectedCoords(sender, args[1], args[2], args[3], args[4], args[5], args[6]);
            default:
                return false;
        }
    }


    private boolean setNoCoords(CommandSender sender, String location) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command with no coordinates");
            return true;
        }


        Player player = (Player) sender;

        Location loc = player.getLocation();

        this.teleports.setCoords(args[1], loc.x(), loc.y(), loc.z(), loc.getYaw(), loc.getPitch(), loc.getWorld().getName());

        return true;
    }

    private boolean setCoords(CommandSender sender, String x, String y, String z) {

        double xSet;
        double ySet;
        double zSet;

        try {
            xSet = Double.parseDouble(x);
            ySet = Double.parseDouble(y);
            zSet = Double.parseDouble(z);

        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid coordinates");
            return true;
        }

        this.teleports.setCoords(args[1], xSet, ySet, zSet, 0, 0, Bukkit.getWorlds().get(0).getName());

        return true;
    }

    private boolean setWorldedCoords(CommandSender sender, String x, String y, String z, String world) {

        double xSet;
        double ySet;
        double zSet;

        try {
            xSet = Double.parseDouble(x);
            ySet = Double.parseDouble(y);
            zSet = Double.parseDouble(z);

        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid coordinates");
            return true;
        }

        if (Bukkit.getWorld(world) == null) {
            sender.sendMessage("Invalid world");
            return false;
        }

        this.teleports.setCoords(args[1], xSet, ySet, zSet, 0, 0, world);

        return true;
    }

    private boolean setDirectedCoords(CommandSender sender, String x, String y, String z, String yaw, String pitch, String world) {

        double xSet;
        double ySet;
        double zSet;
        float yawSet;
        float pitchSet;

        try {
            xSet = Double.parseDouble(x);
            ySet = Double.parseDouble(y);
            zSet = Double.parseDouble(z);
            yawSet = Float.parseFloat(yaw);
            pitchSet = Float.parseFloat(pitch);

        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid coordinates");
            return true;
        }

        this.teleports.setCoords(args[1], xSet, ySet, zSet, yawSet, pitchSet, Bukkit.getWorlds().get(0).getName());

        return true;
    }

    private boolean setWorldedDirectedCoords(CommandSender sender, String x, String y, String z, String yaw, String pitch, String world) {

        double xSet;
        double ySet;
        double zSet;
        float yawSet;
        float pitchSet;

        try {
            xSet = Double.parseDouble(x);
            ySet = Double.parseDouble(y);
            zSet = Double.parseDouble(z);
            yawSet = Float.parseFloat(yaw);
            pitchSet = Float.parseFloat(pitch);

        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid coordinates");
            return true;
        }

        if (Bukkit.getWorld(world) == null) {
            sender.sendMessage("Invalid world");
            return false;
        }

        this.teleports.setCoords(args[1], xSet, ySet, zSet, yawSet, pitchSet, world);

        return true;
    }

}

