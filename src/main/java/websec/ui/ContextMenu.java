/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package websec.ui;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import websec.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContextMenu implements ContextMenuItemsProvider {
    private final MontoyaApi api;

    public static void build(MontoyaApi api) {
        Logger.log("ContextMenu", "Create Context Menu");

        api.userInterface().registerContextMenuItemsProvider(new ContextMenu(api));
    }

    public ContextMenu(MontoyaApi api) {
        this.api = api;
    }

    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event) {
        Logger.log("ContextMenu", event.invocationType().name());

        if (event.isFromTool(ToolType.PROXY, ToolType.TARGET, ToolType.LOGGER)) {
            List<Component> menuItemList = new ArrayList<>();

            JMenuItem retrieveRequestItem = new JMenuItem("Print request");
            HttpRequestResponse requestResponse = event.messageEditorRequestResponse().isPresent()
                    ? event.messageEditorRequestResponse().get().requestResponse()
                    : event.selectedRequestResponses().get(0);

            retrieveRequestItem.addActionListener(l -> {
                Logger.log("ContextMenu", "retrieveRequestItem onClick");
                api.logging().logToOutput("Request is:\r\n" + requestResponse.request().toString());
            });

            menuItemList.add(retrieveRequestItem);

            if (requestResponse.response() != null) {
                JMenuItem retrieveResponseItem = new JMenuItem("Print response");
                retrieveResponseItem.addActionListener(l -> {
                    Logger.log("ContextMenu", "retrieveResponseItem onClick");
                    api.logging().logToOutput("Response is:\r\n" + requestResponse.response().toString());
                });
                menuItemList.add(retrieveResponseItem);
            }

            return menuItemList;
        }
        return null;
    }
}
