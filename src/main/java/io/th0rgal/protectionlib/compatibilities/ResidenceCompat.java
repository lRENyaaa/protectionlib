package io.th0rgal.protectionlib.compatibilities;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import io.th0rgal.protectionlib.ProtectionCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ResidenceCompat extends ProtectionCompatibility {
    public ResidenceCompat(JavaPlugin mainPlugin, Plugin plugin) {
        super(mainPlugin, plugin);
    }

    @Override
    public boolean canBuild(Player player, Location target) {
        return checkPermission(player, target, Flags.place);
    }

    @Override
    public boolean canBreak(Player player, Location target) {
        return checkPermission(player, target, Flags.destroy);
    }

    @Override
    public boolean canInteract(Player player, Location target) {
        // Residence breaks this permission down into multiple permissions, so  only need to match any one of them here.
        return checkPermission(player, target, Flags.container) || checkPermission(player, target, Flags.use);
    }

    @Override
    public boolean canUse(Player player, Location target) {
        // Based on the definition of this method, it seems that Residence does not check this permission.
        return true;
    }

    public boolean checkPermission(Player player, Location target, Flags flag) {
        ClaimedResidence residence = Residence.getInstance().getResidenceManager().getByLoc(target);
        return residence == null || residence.getPermissions().playerHas(player, flag, false);
    }
}
