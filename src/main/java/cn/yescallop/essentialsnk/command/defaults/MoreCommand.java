package cn.yescallop.essentialsnk.command.defaults;

import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.Language;
import cn.yescallop.essentialsnk.command.CommandBase;

public class MoreCommand extends CommandBase {

    public MoreCommand(EssentialsAPI api) {
        super("more", api);

        // command parameters
        commandParameters.clear();
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (!this.testIngame(sender)) {
            return false;
        }
        if (args.length != 0) {
            this.sendUsage(sender);
            return false;
        }
        Player player = (Player) sender;
        if (player.isCreative() || player.isSpectator()) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.more.notavalible"));
            return false;
        }
        Item item = player.getInventory().getItemInMainHand();
        if (item.getId() == Item.get(String.valueOf(Item.AIR)).getId()) {
            sender.sendMessage(TextFormat.RED + Language.translate("commands.more.air"));
            return false;
        }
        item.setCount(item.getMaxStackSize());
        player.getInventory().setItemInHand(item);
        sender.sendMessage(Language.translate("commands.more.success"));
        return true;
    }
}
