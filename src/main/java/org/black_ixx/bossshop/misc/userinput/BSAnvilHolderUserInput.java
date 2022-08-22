package org.black_ixx.bossshop.misc.userinput;

import org.bukkit.entity.Player;

public class BSAnvilHolderUserInput extends BSAnvilHolder {


    private final BSUserInput input;

    public BSAnvilHolderUserInput(BSUserInput input) {
        this.input = input;
    }

    @Override
    public void userClickedResult(Player p) {
        input.receivedInput(p, getOutputText());
    }

}
