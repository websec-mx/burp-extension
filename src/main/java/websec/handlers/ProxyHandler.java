package websec.handlers;

import static burp.api.montoya.core.HighlightColor.RED;
import static burp.api.montoya.http.message.ContentType.JSON;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.proxy.http.InterceptedRequest;
import burp.api.montoya.proxy.http.InterceptedResponse;
import burp.api.montoya.proxy.http.ProxyRequestHandler;
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction;
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction;
import burp.api.montoya.proxy.http.ProxyResponseHandler;
import burp.api.montoya.proxy.http.ProxyResponseReceivedAction;
import burp.api.montoya.proxy.http.ProxyResponseToBeSentAction;
import websec.Logger;

public class ProxyHandler {

    public static void build(MontoyaApi api) {
        Logger.log("ProxyHandler", "Create");

        api.proxy().registerRequestHandler(new RequestHandler());
        api.proxy().registerResponseHandler(new ResponseHandler());

    }

    static class ResponseHandler implements ProxyResponseHandler {
        @Override
        public ProxyResponseReceivedAction handleResponseReceived(InterceptedResponse interceptedResponse) {
            Logger.log("ProxyHandler - Response",
                    "Initial intercepted proxy response from " + interceptedResponse.initiatingRequest().httpService());
            // Highlight all responses that have username in them
            //if (interceptedResponse.bodyToString().contains("username")) {
            //    return ProxyResponseReceivedAction.continueWith(interceptedResponse,
            //            interceptedResponse.annotations().withHighlightColor(BLUE));
            //}

            return ProxyResponseReceivedAction.continueWith(interceptedResponse);
        }

        @Override
        public ProxyResponseToBeSentAction handleResponseToBeSent(InterceptedResponse interceptedResponse) {
            Logger.log("ProxyHandler - Response",
                    "Final intercepted proxy response from " + interceptedResponse.initiatingRequest().httpService());

            return ProxyResponseToBeSentAction.continueWith(interceptedResponse);
        }

    }

    static class RequestHandler implements ProxyRequestHandler {
        @Override
        public ProxyRequestReceivedAction handleRequestReceived(InterceptedRequest interceptedRequest) {
            Logger.log("ProxyHandler - Request",
                    "Initial intercepted proxy request to " + interceptedRequest.httpService());

            // Drop all post requests
            // if (interceptedRequest.method().equals("POST")) {
            // return ProxyRequestReceivedAction.drop();
            // }

            // Do not intercept any request with foo in the url
            // if (interceptedRequest.url().contains("foo")) {
            // return ProxyRequestReceivedAction.doNotIntercept(interceptedRequest);
            // }

            // If the content type is json, highlight the request and follow burp rules for
            // interception
            // if (interceptedRequest.contentType() == JSON) {
            // return ProxyRequestReceivedAction.continueWith(interceptedRequest,
            // interceptedRequest.annotations().withHighlightColor(RED));
            // }

            return ProxyRequestReceivedAction.continueWith(interceptedRequest);
        }

        @Override
        public ProxyRequestToBeSentAction handleRequestToBeSent(InterceptedRequest interceptedRequest) {
            Logger.log("ProxyHandler - Request",
                    "Final intercepted proxy request to " + interceptedRequest.httpService());

            return ProxyRequestToBeSentAction.continueWith(interceptedRequest);
        }
    }

}
