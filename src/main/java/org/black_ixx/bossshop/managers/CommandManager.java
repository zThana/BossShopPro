package org.black_ixx.bossshop.managers;


import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.core.BSShop;
import org.black_ixx.bossshop.managers.features.ShopCreator;
import org.black_ixx.bossshop.managers.item.ItemDataPart;
import org.black_ixx.bossshop.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {

        if (cmd.getName().equalsIgnoreCase("bossshop") || cmd.getName().equalsIgnoreCase("shop") || cmd.getName().equalsIgnoreCase("bs")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("BossShop.reload")) {
                        sender.sendMessage(ClassManager.manager.getMessageHandler().get("Main.StartReload"));
                        ClassManager.manager.getPlugin().reloadPlugin(sender);
                        sender.sendMessage(ClassManager.manager.getMessageHandler().get("Main.ReloadFinish"));
                    } else {
                        ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", sender);
                        return false;
                    }
                    return true;
                }

                if (args[0].equalsIgnoreCase("read")) {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("BossShop.read")) {
                            Player p = (Player) sender;
                            ItemStack item = Misc.getItemInMainHand(p);
                            if (item == null || item.getType() == Material.AIR) {
                                ClassManager.manager.getMessageHandler().sendMessage("Main.NeedItemInHand", sender);
                                return false;
                            }
                            List<String> itemdata = ItemDataPart.readItem(item);
                            ClassManager.manager.getItemDataStorage().addItemData(p.getName(), itemdata);
                            ClassManager.manager.getMessageHandler().sendMessage("Main.PrintedItemInfo", sender);
                            for (String line : itemdata) {
                                sender.sendMessage("- " + line);
                            }
                            return true;
                        } else {
                            ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", sender);
                            return false;
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("simulate")) {
                    if (sender.hasPermission("BossShop.simulate")) {
                        if (args.length == 4) {
                            Player p = Bukkit.getPlayer(args[1]);
                            if (p == null) {
                                ClassManager.manager.getMessageHandler().sendMessage("Main.PlayerNotFound", sender, args[1]);
                                return false;
                            }
                            BSShop shop = ClassManager.manager.getShops().getShop(args[2]);
                            if (shop == null) {
                                ClassManager.manager.getMessageHandler().sendMessage("Main.ShopNotExisting", sender, null, p, null, null, null);
                                return false;
                            }
                            BSBuy buy = shop.getItem(args[3]);
                            if (buy == null) {
                                ClassManager.manager.getMessageHandler().sendMessage("Main.ShopItemNotExisting", sender, null, p, shop, null, null);
                                return false;
                            }
                            buy.click(p, shop, null, null, null, ClassManager.manager.getPlugin());
                            return true;
                        }
                        sendCommandList(sender);
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("close")) {
                    if (sender.hasPermission("BossShop.close")) {
                        Player p = null;
                        String name = sender instanceof Player ? sender.getName() : "CONSOLE";

                        if (sender instanceof Player) {
                            p = (Player) sender;
                        }
                        if (args.length >= 2) {
                            name = args[1];
                            p = Bukkit.getPlayer(name);
                        }

                        if (p == null) {
                            ClassManager.manager.getMessageHandler().sendMessage("Main.PlayerNotFound", sender, name);
                            return false;
                        }

                        p.closeInventory();
                        if (p != sender) {
                            ClassManager.manager.getMessageHandler().sendMessage("Main.CloseShopOtherPlayer", sender, p);
                        }

                    } else {
                        ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", sender);
                        return false;
                    }
                    return true;
                }

                if(args[0].equalsIgnoreCase("help")){
                    sendCommandList(sender);
                    return true;
                }

                if(args[0].equalsIgnoreCase("create") & args.length==3){
                    if(sender.hasPermission("BossShop.create")){
                        Player p = null;
                        if(sender instanceof Player) {
                             p = (Player) sender;
                        }
                        ShopCreator sc = new ShopCreator(ClassManager.manager.getPlugin(), ClassManager.manager.getMessageHandler());
                        sc.startCreate(p, args[1],args[2]);
                        return true;
                    } else {
                        ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", sender);
                    }
                    return false;
                }

                if (args.length >= 3 && args[0].equalsIgnoreCase("open")) {
                    String shopname = args[1].toLowerCase();
                    BSShop shop = ClassManager.manager.getShops().getShop(shopname);
                    String name = args[2];
                    Player p = Bukkit.getPlayerExact(name);
                    String argument = args.length > 3 ? args[3] : null;

                    if (p == null) {
                        p = Bukkit.getPlayer(name);
                    }

                    if (p == null) {
                        ClassManager.manager.getMessageHandler().sendMessage("Main.PlayerNotFound", sender, name, null, shop, null, null);
                        return false;
                    }

                    if (shop == null) {
                        ClassManager.manager.getMessageHandler().sendMessage("Main.ShopNotExisting", sender, null, p, null, null, null);
                        return false;
                    }

                    if (p != sender) {
                        ClassManager.manager.getMessageHandler().sendMessage("Main.OpenShopOtherPlayer", sender, null, p, shop, null, null);
                    }
                    return playerCommandOpenShop(sender, p, shopname, argument);
                }

            }

            if (sender instanceof Player) {
                Player p = (Player) sender;

                String shop;
                if (args.length != 0) {
                    shop = args[0].toLowerCase();
                }else {
                    shop = ClassManager.manager.getPlugin().getConfig().getString("MainShop").toLowerCase();
                }
                String argument = args.length > 1 ? args[1] : null;
                return playerCommandOpenShop(sender, p, shop, argument);
            }
            sendCommandList(sender);
            return false;
        }
        return false;
    }


    private boolean playerCommandOpenShop(CommandSender sender, Player target, String shop, String argument) {
        if (sender == target) {
            if (!(sender.hasPermission("BossShop.open") || sender.hasPermission("BossShop.open.command") || sender.hasPermission("BossShop.open.command." + shop))) {
                ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", sender);
                return false;
            }
        } else {
            if (!sender.hasPermission("BossShop.open.other")) {
                ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", sender);
                return false;
            }
        }
        if (argument != null) {
            ClassManager.manager.getPlayerDataHandler().enteredInput(target, argument);
        }
        if (ClassManager.manager == null) {
            return false;
        }
        if (ClassManager.manager.getShops() == null) {
            return false;
        }
        ClassManager.manager.getShops().openShop(target, shop);
        return true;
    }

    private void sendCommandList(CommandSender s) {
        MessageHandler mh = ClassManager.manager.getMessageHandler();
        mh.sendMessage("Command.Help1",s);
        mh.sendMessage("Command.Help2",s);
        mh.sendMessage("Command.Help3",s);
        mh.sendMessage("Command.Help4",s);
        mh.sendMessage("Command.Help5",s);
        mh.sendMessage("Command.Help6",s);
        if (s instanceof Player) {
            mh.sendMessage("Command.Help7",s);
            mh.sendMessage("Command.Help8",s);
        }
        mh.sendMessage("Command.Help",s);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> arglist = new ArrayList<>();
        if(args.length == 1){
            if(sender.hasPermission("BossShop.open.command")||sender.hasPermission("BossShop.open")) {
                arglist.add("open");
                if(sender instanceof Player){
                    arglist.add("<shop>");
                }
            }
            arglist.add("help");
            if(sender.hasPermission("BossShop.close")) {
                arglist.add("close");
            }
            if(sender instanceof Player) {
                if(sender.hasPermission("BossShop.read")) {
                    arglist.add("read");
                }
                if(sender.hasPermission("BossShop.create")) {
                    arglist.add("create");
                }
            }
            if(sender.hasPermission("BossShop.reload")) {
                arglist.add("reload");
            }
            if(sender.hasPermission("BossShop.simulate")) {
                arglist.add("simulate");
            }
        }
        return arglist;
    }
}
