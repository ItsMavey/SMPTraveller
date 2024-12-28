package SMPTraveller.commands;

import org.bukkit.command.CommandExecutor;

import org.bukkit.command.CommandSender;

import SMPTraveller.utils.Teleports;
import SMPTraveller.utils.SetTeleports;

public class Traveller implements CommandExecutor {

    private final Teleports teleports;

    public Traveller(Teleports teleports) {
        this.teleports = teleports;
    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {


        if (args.length == 0) {
            sender.sendMessage("Please specify a location to travel to");
            return true;
        }

        String subcommand = args[0];

        switch (subcommand) {

            case "spawn":
                Spawn spawn = new Spawn(teleports);
                return spawn.onCommand(sender, command, label, args);

            case "shop":
                Shop shop = new Shop(teleports);
                return shop.onCommand(sender, command, label, args);

            case "end":
                End end = new End(teleports);
                return end.onCommand(sender, command, label, args);

            case "travel":
                Travel travel = new Travel(teleports);
                return travel.onCommand(sender, command, label, args);

            case "set":

                if (!sender.isOp() && !sender.hasPermission("smptraveller.set")) {
                    sender.sendMessage("You do not have permission to use this command");
                    return true;
                }

                if (args.length < 2) {
                    sender.sendMessage("Please specify a location to set");
                    return true;
                }

                SetTeleports setTeleports = new SetTeleports(teleports, args);

                if (setTeleports.set(sender)) {
                    sender.sendMessage("Location §6" + args[1] + "§f set");
                    return true;
                }

                sender.sendMessage("Location §6" + args[1] + "§f not set");
                return false;

            case "help":
                sender.sendMessage("Available subcommands: spawn, shop, end, set, travel");

                if (sender.isOp() || sender.hasPermission("smptraveller.reload")) {
                    sender.sendMessage("Admin subcommands: reload, delete, remove, list");
                }

                sender.sendMessage("For more information join the discord of ItsMavey -> Link Available on Twitch");

                return true;

            case "reload":

                if (!sender.hasPermission("smptraveller.reload")) {
                    sender.sendMessage("You do not have permission to use this command");
                    return true;
                }
                teleports.reload();

                sender.sendMessage("Config reloaded");

                return true;

            case "delete":

            case "remove":

                if (!sender.isOp() && !sender.hasPermission("smptraveller.remove")) {
                    sender.sendMessage("You do not have permission to use this command");
                    return true;
                }

                if (args.length < 2) {
                    sender.sendMessage("Please specify a location to remove");
                    return true;
                }

                this.teleports.remove(args[1]);
                this.teleports.save();

                sender.sendMessage("Location §6" + args[1] + "§f removed");

                return true;


            case "list":
                sender.sendMessage("Locations: " + String.join("\n ", this.teleports.getKeys()));

                return true;

            default:
                sender.sendMessage("The subcommand " + subcommand + " does not exist");
                return true;

        }

    }
}
