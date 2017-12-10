package aws.example.sqs.jms;

import aws.example.sqs.ProxySQSFactory;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class JmsReceiver {
    public static void main(String args[]) throws JMSException {
        ExampleConfiguration config = ExampleConfiguration.parseConfig("JmsReceiver", args);


        //ExampleCommon.setupLogging();

        // Create the connection factory based on the config
        final AmazonSQSClientBuilder standard = ProxySQSFactory.getAmazonSQSClientBuilder(false);
        //standard.setClientConfiguration();
        ConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                standard
                        .withRegion(config.getRegion().getName())
                        .withCredentials(config.getCredentialsProvider())
        );

        // Create the connection
        Connection connection = connectionFactory.createConnection();

        // Create the queue if needed
        //ExampleCommon.ensureQueueExists(connection, config.getQueueName());

        // Create the session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(session.createQueue(config.getQueueName()));

        connection.start();

        receiveMessages(session, consumer);

        // Close the connection. This
    }

    private static void receiveMessages( Session session, MessageConsumer consumer ) {
        try {
            while( true ) {
                System.out.println( "Waiting for messages");
                // Wait 1 minute for a message
                Message message = consumer.receive(TimeUnit.MINUTES.toMillis(1));
                if( message == null ) {
                    System.out.println( "Shutting down after 1 minute of silence" );
                    break;
                }
                ExampleCommon.handleMessage(message);
                message.acknowledge();
                System.out.println( "Acknowledged message " + message.getJMSMessageID() );
            }
        } catch (JMSException e) {
            System.err.println( "Error receiving from SQS: " + e.getMessage() );
            e.printStackTrace();
        }
    }
}
