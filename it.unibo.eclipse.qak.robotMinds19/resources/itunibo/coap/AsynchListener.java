package itunibo.coap;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;

public class AsynchListener implements CoapHandler {
    @Override
    public void onLoad(CoapResponse response) {
        String content = response.getResponseText();
        System.out.println("AsynchListener onLoad: " + content);
    }

    @Override
    public void onError() {
        System.err.println("AsynchListener Error");
    }
}
