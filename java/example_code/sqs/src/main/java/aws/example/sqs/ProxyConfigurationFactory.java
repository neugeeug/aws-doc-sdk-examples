package aws.example.sqs;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;

public class ProxyConfigurationFactory {

    public static ClientConfiguration create(String host, String port) {
        ClientConfiguration cfg = new ClientConfiguration();
        cfg.setProxyHost(host);
        cfg.setProxyPort( Integer.valueOf(port));
        cfg.setProtocol(Protocol.HTTP);
        return cfg;
    }

}
