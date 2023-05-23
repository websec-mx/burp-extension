package websec.handlers;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.scanner.audit.AuditIssueHandler;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import websec.Logger;

public class ScannerHandler {

    public static void build(MontoyaApi api) {
        Logger.log("ScannerHandler", "Create");

        api.scanner().registerAuditIssueHandler(new AuditHandler());
    }

    static class AuditHandler implements AuditIssueHandler {
        @Override
        public void handleNewAuditIssue(AuditIssue auditIssue) {
            Logger.log("ScannerHandler", 
                        "New scan issue: " + auditIssue.name()
            );
        }
    }
}
