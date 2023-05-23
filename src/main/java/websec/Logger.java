package websec;

import java.util.Calendar;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.logging.Logging;
import websec.models.LoggerModel;
import websec.models.ResponseModel;
import websec.ui.LoggerView;

public class Logger {

    static Logging logging;
    static LoggerModel loggerModel = new LoggerModel();
    static ResponseModel responseModel = new ResponseModel();
    
    public static void build(MontoyaApi api) {
        logging = api.logging();

        new LoggerView(api, loggerModel, responseModel);
    }

    public static void log(String source, String message) {
        logging.logToOutput(message);
        loggerModel.add(Calendar.getInstance().getTime().toString(),source, message);
    }

    public static void newPacket(HttpResponseReceived packet) {
        logging.logToOutput("Logger: new packet");
        responseModel.add(packet);
    }

}
