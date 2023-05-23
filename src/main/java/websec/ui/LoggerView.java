package websec.ui;

import static burp.api.montoya.ui.editor.EditorOptions.READ_ONLY;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.ui.UserInterface;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;

import websec.models.LoggerModel;
import websec.models.ResponseModel;

public class LoggerView {

    public LoggerView(MontoyaApi api, LoggerModel loggerModel, ResponseModel responseModel) {

        UserInterface userInterface = api.userInterface();

        HttpRequestEditor requestViewer = userInterface.createHttpRequestEditor(READ_ONLY);
        HttpResponseEditor responseViewer = userInterface.createHttpResponseEditor(READ_ONLY);

        JTable responseTable = new JTable(responseModel) {
            @Override
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                HttpResponseReceived responseReceived = responseModel.get(rowIndex);
                requestViewer.setRequest(responseReceived.initiatingRequest());
                responseViewer.setResponse(responseReceived);
                super.changeSelection(rowIndex, columnIndex, toggle, extend);
            }
        };

        JTable loggerTable = new JTable(loggerModel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Logs", new JScrollPane(loggerTable));
        tabs.addTab("Request", requestViewer.uiComponent());
        tabs.addTab("Response", responseViewer.uiComponent());

        splitPane.setRightComponent(tabs);

        splitPane.setLeftComponent(new JScrollPane(responseTable));

        api.userInterface().registerSuiteTab("Websec Logger", splitPane);
    }
}
