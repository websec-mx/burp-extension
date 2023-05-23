/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package websec.ui;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.ui.menu.BasicMenuItem;
import burp.api.montoya.ui.menu.MenuItem;
import websec.Logger;

public class Menu {

    public static void build(MontoyaApi api) {
        Logger.log("Menu", "Create Menu");

        BasicMenuItem menu1Item = BasicMenuItem
                .basicMenuItem("Websec - BasicMenuItem")
                .withAction(() -> 
                    Logger.log("Menu", "onBasicMenuItemClick")
                );

        BasicMenuItem menu2Item = MenuItem
                .basicMenuItem("Websec - MenuItem")
                .withAction(() -> 
                    Logger.log("Menu", "onMenuItemClick")
                );

        burp.api.montoya.ui.menu.Menu menu = burp.api.montoya.ui.menu.Menu
                .menu("Websec Menu")
                .withMenuItems(menu1Item, menu2Item);

        api.userInterface()
            .menuBar()
            .registerMenu(menu);
    }
}
