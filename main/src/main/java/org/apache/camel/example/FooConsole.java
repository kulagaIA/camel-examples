package org.apache.camel.example;

import org.apache.camel.spi.annotations.DevConsole;
import org.apache.camel.support.console.AbstractDevConsole;
import org.apache.camel.util.json.JsonObject;

import java.util.Map;

@DevConsole(name = "foo_name", displayName = "foo", description = "foo console")
public class FooConsole extends AbstractDevConsole {

    public FooConsole() {
        super("acme", "foo", "Foolish", "A foolish console that outputs something");
    }

    @Override
    protected String doCallText(Map<String, Object> options) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello from my custom console");

        // more stuff here

        return sb.toString();
    }

    @Override
    protected JsonObject doCallJson(Map<String, Object> options) {
        JsonObject root = new JsonObject();
        root.put("message", "Hello from my custom console");

        // more stuff here

        return root;
    }

}