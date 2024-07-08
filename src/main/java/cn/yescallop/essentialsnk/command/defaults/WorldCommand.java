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

    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        var args = result.getKey();
        if (!this.testPermission(sender)) {
            return 0;
        }
        if (!this.testIngame(sender)) {
            return 0;
        }
        if (args.length() != 1) {
            this.sendUsage(sender);
            return 0;
        }
        if (api.hasCooldown(sender)) {
            return 1;
        }
        if (api.getServer().getLevelByName(args[0])==null) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.world.notfound", args[0]));
            return 1;
        } else if (!api.getServer().isLevelLoaded(args[0])) {
            sender.sendMessage(Language.translate("commands.world.loading"));
            if (!api.getServer().loadLevel(args[0])) {
                sender.sendMessage(TextFormat.RED + Language.translate("commands.world.unloadable"));
                return 0;
            }
        }
        api.onTP((Player) sender, api.getServer().getLevelByName(args[0]).getSpawnLocation(), Language.translate("commands.generic.teleporting"));
        return 1;
    }
}
