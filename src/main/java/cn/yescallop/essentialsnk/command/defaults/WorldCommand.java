package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.tree.ParamList;
import cn.nukkit.command.utils.CommandLogger;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;
import lombok.var;

import java.util.LinkedList;
import java.util.Map;

public class WorldCommand extends CommandBase {

    public WorldCommand(EssentialsAPI api) {
        super("world", api);

        // command parameters
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("world", false, CommandParamType.TEXT)
        });
        //KailynDev2024®
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (!this.testIngame(sender)) {
            return false;
        }
        if (args.length != 1) {
            this.sendUsage(sender);
            return false;
        }
        if (api.hasCooldown(sender)) {
            return true;
        }
        if (api.getServer().getLevelByName(args[0])==null) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.world.notfound", args[0]));
            return true;
        } else if (!api.getServer().isLevelLoaded(args[0])) {
            sender.sendMessage(Language.translate("commands.world.loading"));
            if (!api.getServer().loadLevel(args[0])) {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.world.unloadable"));
                return false;
            }
        }
        api.onTP((Player) sender, api.getServer().getLevelByName(args[0]).getSpawnLocation(), Language.translate("commands.generic.teleporting"));
        return true;
    }
}
