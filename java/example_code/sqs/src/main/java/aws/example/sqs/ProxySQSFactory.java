package aws.example.sqs;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class ProxySQSFactory {

    public  static final String TEST_QUEUE_NAME = "TestEne";

    public static AmazonSQS create(boolean proxy) {

        AmazonSQSClientBuilder awsBuilder = getAmazonSQSClientBuilder(proxy);
        return awsBuilder.withRegion("eu-west-1").build();
    }

    public static AmazonSQSClientBuilder getAmazonSQSClientBuilder(boolean proxy) {
        return getAmazonSQSClientBuilder(proxy, "vmhost", "8080");
    }

    public static AmazonSQSClientBuilder getAmazonSQSClientBuilder(boolean proxy, String host, String port) {
        AmazonSQSClientBuilder awsBuilder = AmazonSQSClientBuilder.standard();
        if (proxy) {
            awsBuilder.setClientConfiguration(ProxyConfigurationFactory.create(host, port));
        }
        return awsBuilder;
    }

}
