package SMPTraveller.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import SMPTraveller.utils.Teleports;

public class Shop implements CommandExecutor{

    private final Teleports teleports;

    public Shop(Teleports teleports) {
        this.teleports = teleports;
    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }

        Player player = (Player) sender;

        if (!teleports.exists("shop")) {
            player.sendMessage("Shop location not set");
            return true;
        }

        this.teleports.teleport(player, "shop");

        player.sendMessage("Teleported to the shopping district");

        return true;
    }
}
