Hello World Example Extension
============================

###### Prints output to various locations within Burp.

 ---

This extension is about as basic as things can get, while actually
doing something.

It demonstrates the following techniques:
- Setting the name of the extension, which will be shown to the user in the UI.
- Writing messages to the output and error streams.
- Writing messages to the main Burp alerts log.
- Generating an exception to demonstrate how this is reported to the user.

Menu Bar Example Extension
============================

###### Registers a top level menu bar with actions.

 ---

This extension demonstrates registering a top level menu bar item with various actions.

It demonstrates the following techniques:
- Creating a `BasicMenuItem` with a caption
- Providing an action for a `BasicMenuItem`
- Adding the `Menu` to Burp
- Registering an `ExtensionUnloadingHandler`

Custom Intruder Payloads Example Extension
============================

###### Provides custom Intruder payloads and payload processing.

 ---

This example shows how you can use an extension to:
- Generate custom Intruder payloads
- Apply custom processing to Intruder payloads (including built-in ones)

When an extension registers itself as an Intruder payload provider, this will be available within the Intruder UI for the user to select as the payload source for an attack. When an extension registers itself as a payload processor, the user can create a payload processing rule and select the extension's processor as the rule's action.

The extension uses the following techniques:
- Registers a new `PayloadGeneratorProvider`, which returns a new `PayloadGenerator`
- Registers a new `PayloadProcessor`
- The `PayloadGenerator` does the following:
  - Contains a list of payloads to be used
  - Iterates through the payload list, until there are no longer any payloads available
- The `PayloadProcessor` does the following:
  - Decodes the base value of the payload
  - Parses the location of the `input` string in the decoded data
  - Rebuilds the serialized data with the new payload


HTTP Handler Example Extension
============================

###### Demonstrates performing various actions on requests passing through any tool in Burp

 ---

The extension works as follows:
- It registers an HTTP handler
- For outgoing request messages:
    - If the request is a `POST` request:
        - The body of the request is logged to output
        - A comment is added to the request
    - A URL parameter is added to the request
- For incoming response messages:
    - If the corresponding request contained a `Content-Length` header, a `BLUE` highlight is added


Event Listeners Example Extension
============================

###### Registers handlers for various runtime events, and prints a message when each event occurs.

 ---

This extension demonstrates how to register listeners for various runtime
events:
- HTTP requests and responses for all Burp tools.
- HTTP messages intercepted by the Proxy.
- Addition of new scan issues.
- The extension being unloaded by the user.

The sample extension simply prints a message to the output stream when an event
occurs.

Custom Session Tokens Example Extension
============================

###### Demonstrates working with custom session tokens that Burp doesn't normally understand.

 ---

This example demonstrates how you can couple a recorded macro with an extension to automatically gain a session token for a website and use it in later requests that Burp makes.

The macro mechanism that Burp provides allows you to record the request triggering creation of a session made via the proxy.

The extension uses the following techniques:
- Registers a `SessionHandlingAction` handler
- Fetches the list of macro requests and responses
- Extracts the response headers from the last `HttprequestResponse` item in the list
- Finds the relevant session header (in this example, this header is `X-Custom-Session-Id`)
- Returns an `HttpRequest` with an updated session header

Custom Scan Checks Example Extension
============================

###### Provides custom attack insertion points for active scanning.

---

The sample extension demonstrates the following techniques:
- Registering a custom `AuditInsertionPointProvider`
- If the request contains the `data` parameter, it will provide a custom `AuditInsertionPoint`
- The custom `AuditInsertionPoint` will perform the following:
  - Deserialize the data (URL decode and then Base64 decode)
  - Parse the location of the `input=` string withing the decoded data
  - Split the data into a prefix, location to place the payload, and a suffix
  - When building the request with the appropriate payload, it will perform the following:
    - Build the raw data with the appropriate payload
    - Re-serialize the data (Base64 encode then URL encode)


Custom Scan Checks Example Extension
============================

###### Implements custom checks to extend the capabilities of Burp's active and passive scanning engines.

---

The sample extension demonstrates the following techniques:
- Registering a custom scan check
- Performing passive and active scanning when initiated by the user
- Using the Burp-provided `AuditInsertionPoint` to construct requests for active scanning using specified payloads
- Using a helper method to search responses for relevant match strings
- Providing an `MarkedHttpRequestResponse` to highlight relevant portions of requests and responses, 
- Synchronously reporting custom scan issues in response to the relevant checks.
- Guiding Burp on when to consolidate duplicated issues at the same URL (e.g., when the user has scanned the same item multiple times).


Custom Request Editor Tab Example Extension
============================

###### Adds a new tab to Burp's HTTP message editor, in order to handle a data serialization format

 ---

This extension provides a new tab on the message editor for requests that contain a specified parameter.

The extension uses the following techniques:
- It creates a custom request tab on the message editor, provided that the `data` parameter is present
- If it is appropriate, the editor is set to be read-only
- The value of the `data` parameter is deserialized (URL decoded, then Base64 decoded) and displayed in the custom tab
- If the value of the data is modified, the content will be re-serialized (Base64 encoded then URL encoded) and updated in the HttpRequest


Custom Logger Example Extension
============================

###### Adds a new tab to Burp's UI and displays a log of HTTP traffic for all Burp tools.

 ---

This extension provides a suite-wide HTTP logger within the main Burp UI.

The extension uses the following techniques:
- It creates a custom tab within the main Burp UI, in which to display logging user interface
- It displays a table of items and a read-only editor for requests and responses within a splitpane
- When an item passes through the HttpHandler, it gets added to the table
- You can view the request and response for an item in the table by clicking on the relevant row

Context Menu Example Extension
============================

###### Registers new context menu items to print requests and responses.

---
This extension adds a new context menu item to print out the request or response of an HttpRequestResponse in the Target, Proxy or Logger tab.

The sample extension demonstrates the following techniques:
- Registering a new `ContextMenuItemsProvider`.
- Creating a `JMenuItem`.
- Adding an action listener to a `JMenuItem`.
- If you right-click in a message editor context, it will use the item from the message editor.
- If you right-click on a table item, it will print the request/response for the first selected item.


Proxy Handler Example Extension
============================

###### Demonstrates performing various actions on requests passing through the Proxy

 ---

The extension works as follows:
- It registers a Proxy handler
- For outgoing request messages:
    - It drops all `POST` requests
    - Requests with `foo` in the URL are not intercepted
    - If the `Content-Type` is `JSON`, the request is highlighted `RED` and Burp rules for Interception are followed
    - All other requests are intercepted
    - User modified requests are continued as normal
- For incoming response messages:
    - All responses that contain the string `username` are highlighted `BLUE`
    - All other responses follow Burp rules for Interception

Proxy WebSocket Handler Example Extension
=========================================

###### Demonstrates performing various actions on websocket messages passing through the Proxy

 ---

The extension works as follows:
- It registers a Proxy web socket creation handler
- When a Proxy web socket is created
  - It registers a proxy web socket message listener for the created websocket
  - Any message that contains the text "username" is highlighted in red.
  - Any message from the client that contains the text "password" is force intercepted.



Traffic Redirector Example Extension
============================

###### Redirects all outbound requests from one host to another.

 ---

This extension demonstrates how to redirect outgoing HTTP requests from one host to another. This task might arise, for example, if you have mapped out an application which then moves to a different staging URL. By simply redirecting traffic to the new hostname, you can continue to drive your testing from the original site map.

The extension works as follows:
- It registers an HTTP handler.
- For outgoing request messages, it retrieves the HTTP service for the request.
- If the HTTP service host matches the "from" host, builds an HTTP service using the "to" host, and other details unchanged.
- It returns the HTTP request with the new HTTP service.

**Note:** The sample code uses "host1.example.org" and "host2.example.org" as the "from" and "to" hostnames. You should edit the code to use your own hostnames before using it.


WebSocket Handler Example Extension
===================================

###### Demonstrates performing various actions on websocket messages passing through any tool in Burp

 ---

The extension works as follows:
- It registers a web socket created handler
- When a web socket is created
  - It sends an initial text message
  - It registers a message listener for the websocket
  - Any message from the client that contains the text "password" is base64 encoded.