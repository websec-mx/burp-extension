/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package websec;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class WebsecExtension implements BurpExtension
{

    @Override
    public void initialize(MontoyaApi api)
    {
        api.extension().setName("Websec BAPP");

        websec.Logger.build(api);
        websec.Logger.log("Websec", "Welcome to Websec BAPP!");

        //Create Menu and Context Menu
        websec.ui.ContextMenu.build(api);
        websec.ui.Menu.build(api);

        //Create Handlers
        websec.handlers.HttpHandler.build(api);
        websec.handlers.SessionHandler.build(api);
        websec.handlers.WebSocketHandler.build(api);
        websec.handlers.ScannerHandler.build(api);
        websec.handlers.WebSocketHandler.build(api);
        
        
        websec.Logger.log("Websec", "Extension initialized");

    }
}
