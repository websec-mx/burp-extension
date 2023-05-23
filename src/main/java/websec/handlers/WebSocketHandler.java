package websec.handlers;

import static burp.api.montoya.core.HighlightColor.RED;
import static burp.api.montoya.http.message.ContentType.JSON;
import static burp.api.montoya.websocket.Direction.CLIENT_TO_SERVER;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.proxy.http.InterceptedRequest;
import burp.api.montoya.proxy.http.InterceptedResponse;
import burp.api.montoya.proxy.http.ProxyRequestHandler;
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction;
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction;
import burp.api.montoya.proxy.http.ProxyResponseHandler;
import burp.api.montoya.proxy.http.ProxyResponseReceivedAction;
import burp.api.montoya.proxy.http.ProxyResponseToBeSentAction;
import burp.api.montoya.proxy.websocket.BinaryMessageReceivedAction;
import burp.api.montoya.proxy.websocket.BinaryMessageToBeSentAction;
import burp.api.montoya.proxy.websocket.InterceptedBinaryMessage;
import burp.api.montoya.proxy.websocket.InterceptedTextMessage;
import burp.api.montoya.proxy.websocket.ProxyMessageHandler;
import burp.api.montoya.proxy.websocket.ProxyWebSocketCreation;
import burp.api.montoya.proxy.websocket.ProxyWebSocketCreationHandler;
import burp.api.montoya.proxy.websocket.TextMessageReceivedAction;
import burp.api.montoya.proxy.websocket.TextMessageToBeSentAction;
import burp.api.montoya.utilities.Utilities;
import burp.api.montoya.websocket.BinaryMessage;
import burp.api.montoya.websocket.BinaryMessageAction;
import burp.api.montoya.websocket.TextMessage;
import burp.api.montoya.websocket.TextMessageAction;
import burp.api.montoya.websocket.WebSocketCreated;
import burp.api.montoya.websocket.WebSocketCreatedHandler;

import websec.Logger;

public class WebSocketHandler {

    static Utilities utilities = null;

    public static void build(MontoyaApi api) {
        Logger.log("WebSocketHandler", "Create");

        utilities = api.utilities();

        api.proxy().registerWebSocketCreationHandler(new ProxyCreationHandler());
        api.websockets().registerWebSocketCreatedHandler(new WebSocketCreateHandler());
    }

    static class ProxyCreationHandler implements ProxyWebSocketCreationHandler {
        @Override
        public void handleWebSocketCreation(ProxyWebSocketCreation webSocketCreation) {
            Logger.log("WebSocketHandler", "onHandleWebSocketCreation");
            webSocketCreation.proxyWebSocket().registerProxyMessageHandler(new ProxyMessageHandler());
        }
    }

    static class ProxyMessageHandler implements burp.api.montoya.proxy.websocket.ProxyMessageHandler {

        @Override
        public TextMessageReceivedAction handleTextMessageReceived(InterceptedTextMessage interceptedTextMessage) {
            Logger.log("WebSocketHandler", "onHandleTextMessageReceived");

            // if (interceptedTextMessage.payload().contains("username")) {
            // interceptedTextMessage.annotations().setHighlightColor(HighlightColor.RED);
            // }
            // if (interceptedTextMessage.direction() == CLIENT_TO_SERVER
            // && interceptedTextMessage.payload().contains("password")) {
            // return TextMessageReceivedAction.intercept(interceptedTextMessage);
            // }
            return TextMessageReceivedAction.continueWith(interceptedTextMessage);
        }

        @Override
        public TextMessageToBeSentAction handleTextMessageToBeSent(InterceptedTextMessage interceptedTextMessage) {
            Logger.log("WebSocketHandler", "onHandleTextMessageToBeSent");
            return TextMessageToBeSentAction.continueWith(interceptedTextMessage);
        }

        @Override
        public BinaryMessageReceivedAction handleBinaryMessageReceived(
                InterceptedBinaryMessage interceptedBinaryMessage) {
            Logger.log("WebSocketHandler", "onHandleBinaryMessageReceived");

            return BinaryMessageReceivedAction.continueWith(interceptedBinaryMessage);
        }

        @Override
        public BinaryMessageToBeSentAction handleBinaryMessageToBeSent(
                InterceptedBinaryMessage interceptedBinaryMessage) {
            Logger.log("WebSocketHandler", "onHandleBinaryMessageToBeSent");

            return BinaryMessageToBeSentAction.continueWith(interceptedBinaryMessage);
        }
    }

    static class WebSocketCreateHandler implements WebSocketCreatedHandler {
        @Override
        public void handleWebSocketCreated(WebSocketCreated webSocketCreated) {
            Logger.log("WebSocketHandler", "onHandleWebSocketCreated");

            //webSocketCreated.webSocket().sendTextMessage("First Message");

            webSocketCreated.webSocket().registerMessageHandler(new WebSocketMessageHandler());
        }
    }

    static class WebSocketMessageHandler implements burp.api.montoya.websocket.MessageHandler {
        @Override
        public TextMessageAction handleTextMessage(TextMessage textMessage) {
            Logger.log("WebSocketHandler", "onHandleTextMessage");

            // if (textMessage.direction() == CLIENT_TO_SERVER &&
            // textMessage.payload().contains("password")) {
            // String base64EncodedPayload =
            // utilities.base64Utils().encodeToString(textMessage.payload());
            // return TextMessageAction.continueWith(base64EncodedPayload);
            // }

            return TextMessageAction.continueWith(textMessage);
        }

        @Override
        public BinaryMessageAction handleBinaryMessage(BinaryMessage binaryMessage) {
            Logger.log("WebSocketHandler", "onHandleBinaryMessage");
            return BinaryMessageAction.continueWith(binaryMessage);
        }
    }

}
