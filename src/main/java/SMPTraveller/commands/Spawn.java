package SMPTraveller.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import SMPTraveller.utils.Teleports;

public class Spawn implements CommandExecutor {

    private final Teleports teleports;

    public Spawn(Teleports teleports) {
        this.teleports = teleports;
    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }

        Player player = (Player) sender;

        if (!teleports.exists("spawn")) {
            player.sendMessage("Spawn location not set");
            return true;
        }

        this.teleports.teleport(player, "spawn");

        player.sendMessage("Teleported to spawn");

        return true;
    }

}
