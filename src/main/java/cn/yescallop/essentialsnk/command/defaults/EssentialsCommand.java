package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

import java.util.Arrays;
import java.util.LinkedList;

public class EssentialsCommand extends CommandBase {
    public EssentialsCommand(EssentialsAPI api) {
        super("essentials", api);
        this.setAliases(new String[]{"ess"});
        this.commandParameters.clear();
        this.commandParameters.put("defaults", new CommandParameter[]{
                CommandParameter.newEnum("essOption", true, new String[]{"reload"})
        });
        this.enableParamTree();
    }


    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(Language.translate("essentials.version", api.getVersion()));
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        if (!args[0].equalsIgnoreCase("reload") || !sender.hasPermission("essentialsnk.reload")) {
            return false;
        }
        api.reload();
        sender.sendMessage(Language.translate("essentials.reloaded"));
        return true;
    }
}
