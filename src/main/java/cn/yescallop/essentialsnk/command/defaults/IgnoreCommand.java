package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

import java.util.LinkedList;

public class IgnoreCommand extends CommandBase {

    public IgnoreCommand(EssentialsAPI api) {
        super("ignore", api);

        // command parameters
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("player", false, CommandParamType.TARGET)
        });
        this.enableParamTree();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender) || !this.testIngame(sender)) {
            return false;
        }

        if (args.length == 0) {
            this.sendUsage(sender);
            return false;
        }

        Player player = (Player) sender;

        IPlayer toIgnore = player.getServer().getPlayer(args[0]);

        if (toIgnore == null) {
            toIgnore = player.getServer().getOfflinePlayer(args[0]);

            if (toIgnore.getUniqueId() == null) {
                this.sendUsage(sender);
                return false;
            }
        }
        if (toIgnore == player) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.ignore.self"));
        }

        if (api.ignore(player.getUniqueId(), toIgnore.getUniqueId())) {
            sender.sendMessage("Successfully ignored");
        } else {
            sender.sendMessage("Successfully un-ignored");
        }
        return true;
    }
}
