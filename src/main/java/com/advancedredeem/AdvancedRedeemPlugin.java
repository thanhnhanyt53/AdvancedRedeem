package com.advancedredeem;

import com.advancedredeem.api.AdvancedRedeemAPI;
import com.advancedredeem.command.RedeemAdminCommand;
import com.advancedredeem.command.RedeemCommand;
import com.advancedredeem.condition.ConditionManager;
import com.advancedredeem.economy.EconomyManager;
import com.advancedredeem.gui.MenuListener;
import com.advancedredeem.item.ItemProviderManager;
import com.advancedredeem.reward.RewardManager;
import com.advancedredeem.service.RedeemService;
import com.advancedredeem.storage.CodeManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedRedeemPlugin extends JavaPlugin {

    private CodeManager codeManager;
    private RewardManager rewardManager;
    private ConditionManager conditionManager;
    private EconomyManager economyManager;
    private ItemProviderManager itemProviderManager;
    private RedeemService redeemService;
    private AdvancedRedeemAPI api;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        codeManager = new CodeManager(this);
        rewardManager = new RewardManager(this);
        conditionManager = new ConditionManager(this);
        economyManager = new EconomyManager(this);
        itemProviderManager = new ItemProviderManager(this);

        redeemService = new RedeemService(
                this,
                codeManager,
                rewardManager,
                conditionManager
        );

        api = new AdvancedRedeemAPI(this);

        itemProviderManager.registerBukkitProvider();
        economyManager.registerVaultProvider();

        codeManager.load();

        RedeemCommand redeemCommand =
                new RedeemCommand(this);

        RedeemAdminCommand adminCommand =
                new RedeemAdminCommand(this);

        getCommand("redeem")
                .setExecutor(redeemCommand);

        getCommand("redeemcode")
                .setExecutor(adminCommand);

        getCommand("redeemcode")
                .setTabCompleter(adminCommand);

        getServer()
                .getPluginManager()
                .registerEvents(
                        new MenuListener(this),
                        this
                );

        getLogger().info(
                "AdvancedRedeem enabled."
        );
    }

    @Override
    public void onDisable() {
        if (codeManager != null) {
            codeManager.save();
        }
    }

    public CodeManager codes() {
        return codeManager;
    }

    public RewardManager rewards() {
        return rewardManager;
    }

    public ConditionManager conditions() {
        return conditionManager;
    }

    public EconomyManager economies() {
        return economyManager;
    }

    public ItemProviderManager items() {
        return itemProviderManager;
    }

    public RedeemService redeem() {
        return redeemService;
    }

    public AdvancedRedeemAPI api() {
        return api;
    }
}