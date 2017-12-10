package aws.example.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.Date;

public class SendMessages {


    public static void main(String[] args) {

        AmazonSQS sqs = ProxySQSFactory.create(false);

        String queueUrl = sqs.getQueueUrl(ProxySQSFactory.TEST_QUEUE_NAME).getQueueUrl();
        System.out.println(queueUrl);

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody("hello world " + new Date())
                .withDelaySeconds(0);
        sqs.sendMessage(send_msg_request);



        //        // Send multiple messages to the queue
//        SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
//                .withQueueUrl(queueUrl)
//                .withEntries(
//                        new SendMessageBatchRequestEntry(
//                                "msg_1", "Hello from message 1"),
//                        new SendMessageBatchRequestEntry(
//                                "msg_2", "Hello from message 2")
//                                .withDelaySeconds(10));
//        sqs.sendMessageBatch(send_batch_request);
    }

}
