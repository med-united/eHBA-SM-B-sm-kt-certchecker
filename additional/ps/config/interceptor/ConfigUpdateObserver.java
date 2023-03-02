package health.ere.ps.config.interceptor;

import health.ere.ps.event.config.UserConfigurationsUpdateEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;

@ApplicationScoped
public class ConfigUpdateObserver {

    private boolean updated = false;

    public void handleUpdateProperties(@ObservesAsync UserConfigurationsUpdateEvent event) {
        updated = true;
    }

    public boolean pullValue() {
        boolean result = updated;
        updated = false;
        return result;
    }
}
