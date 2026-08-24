package cn.yescallop.essentialsnk.command.defaults;

import org.powernukkitx.command.CommandSender;
import org.powernukkitx.command.data.CommandParameter;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.command.CommandBase;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;

import java.util.LinkedList;

public class BroadcastCommand extends CommandBase {

    public BroadcastCommand(EssentialsAPI api) {
        super("broadcast", api);
        this.setAliases(new String[]{"bcast"});

        // command parameters
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("message", true, CommandParamType.MESSAGE)
        });
        //KailynDev2024®
    }


    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (args.length == 0) {
            this.sendUsage(sender);
            return false;
        }
        api.getServer().broadcastMessage(String.join(" ", args));
        return true;
    }
}
