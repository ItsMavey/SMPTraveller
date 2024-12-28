package SMPTraveller.commands;



import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import SMPTraveller.utils.Teleports;

public class Travel implements CommandExecutor {

    private final Teleports teleports;

    public Travel(Teleports teleports) {
        this.teleports = teleports;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }


        Player player = (Player) sender;


        // Check if a location has been given

        if (args.length == 0) {
            player.sendMessage("Please specify a location to travel to");
            return true;
        }


        // Check if the location exists

        if (!teleports.exists(args[0])) {
            player.sendMessage("Location not set");
            return true;
        }


        // Teleport the player to the location

        this.teleports.teleport(player, args[0]);

        player.sendMessage("Teleported to " + args[0]);

        return true;

    }
}