package websec.handlers;

import static burp.api.montoya.http.sessions.ActionResult.actionResult;

import java.util.List;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.sessions.ActionResult;
import burp.api.montoya.http.sessions.SessionHandlingAction;
import burp.api.montoya.http.sessions.SessionHandlingActionData;
import websec.Logger;

public class SessionHandler implements SessionHandlingAction {

    public static void build(MontoyaApi api) {
        Logger.log("SessionHandler", "Create");
        api.http().registerSessionHandlingAction(new SessionHandler());
    }

    @Override
    public String name()
    {
        return "SessionHandler";
    }

    @Override
    public ActionResult performAction(SessionHandlingActionData actionData)
    {
        Logger.log("SessionHandler", "onPerformAction");
        
        //ActionResult result = actionResult(actionData.request(), actionData.annotations());
        //List<HttpRequestResponse> macroRequestResponseList = actionData.macroRequestResponses();
        //if (macroRequestResponseList.isEmpty())
        //{
        //    return result;
        //}
        //// Extract the response headers
        //List<HttpHeader> headers = macroRequestResponseList.get(macroRequestResponseList.size()-1).response().headers();
        //// Find session header
        //HttpHeader sessionHeader = findSessionHeader(headers);

        //// If we failed to find a session token, stop doing work
        //if (sessionHeader == null)
        //{
        //    return result;
        //}

        // Create an HTTP request with updated session header
        //HttpRequest updatedRequest = actionData.request().withUpdatedHeader(sessionHeader);

        return actionResult(actionData.request(), actionData.annotations());
    }

    private HttpHeader findSessionHeader(List<HttpHeader> headers)
    {
        Logger.log("SessionHandler", "onFindSessionHeader");
        
        HttpHeader sessionHeader = null;

        for(HttpHeader header : headers)
        {
            // Skip any header that isn't an "X-Custom-Session-Id"
            //if (!header.name().equals("X-Custom-Session-Id"))
            //{
            //    continue;
            //}

            // Grab the session token
            sessionHeader = header;
        }

        return sessionHeader;
    }
   
}
