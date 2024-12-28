package SMPTraveller.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import SMPTraveller.utils.Teleports;

public class End implements CommandExecutor {

    private final Teleports teleports;

    public End(Teleports teleports) {
        this.teleports = teleports;
    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }

        Player player = (Player) sender;

        if (!teleports.exists("end")) {
            player.sendMessage("End location not set");
            return true;
        }

        this.teleports.teleport(player, "end");

        player.sendMessage("Teleported to the end portal");

        return true;
    }

}
