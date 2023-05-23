package websec.handlers;

import static burp.api.montoya.http.handler.RequestToBeSentAction.continueWith;
import static burp.api.montoya.http.handler.ResponseReceivedAction.continueWith;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Annotations;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.http.handler.RequestToBeSentAction;
import burp.api.montoya.http.handler.ResponseReceivedAction;
import websec.Logger;

public class HttpHandler implements burp.api.montoya.http.handler.HttpHandler {

    public static void build(MontoyaApi api) {
        Logger.log("HttpHandler", "Create");
        api.http().registerHttpHandler(new HttpHandler(api));
    }

    public HttpHandler(MontoyaApi api) {
    }

    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent request) {
        Logger.log("HttpHandler", "onHandleHttpRequestToBeSent");

        Annotations annotations = request.annotations();

        //Modify the request by adding url param.
        //HttpRequest modifiedRequest = request.withAddedParameters(
        //            urlParameter("foo", "bar")
        //);
        
        return continueWith(request, annotations);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {
        Logger.log("HttpHandler", "onHandleHttpResponseReceived");

        Annotations annotations = responseReceived.annotations();
        if (responseHasContentLengthHeader(responseReceived)) {
            annotations = annotations.withHighlightColor(HighlightColor.BLUE);
        }

        Logger.newPacket(responseReceived);
        
        return continueWith(responseReceived, annotations);
    }

    private static boolean isPost(HttpRequestToBeSent httpRequestToBeSent) {
        return httpRequestToBeSent.method().equalsIgnoreCase("POST");
    }

    private static boolean isGet(HttpRequestToBeSent httpRequestToBeSent) {
        return httpRequestToBeSent.method().equalsIgnoreCase("GET");
    }

    private static boolean isPut(HttpRequestToBeSent httpRequestToBeSent) {
        return httpRequestToBeSent.method().equalsIgnoreCase("PUT");
    }

    private static boolean isOption(HttpRequestToBeSent httpRequestToBeSent) {
        return httpRequestToBeSent.method().equalsIgnoreCase("OPTION");
    }

    private static boolean isDelete(HttpRequestToBeSent httpRequestToBeSent) {
        return httpRequestToBeSent.method().equalsIgnoreCase("DELETE");
    }

    private static boolean responseHasContentLengthHeader(HttpResponseReceived httpResponseReceived) {
        return httpResponseReceived.initiatingRequest().headers().stream().anyMatch(header -> header.name().equalsIgnoreCase("Content-Length"));
    }
}
