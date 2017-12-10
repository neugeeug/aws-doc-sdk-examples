/*
 * Copyright 2011-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
package aws.example.sqs;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;

import java.util.List;


//c2
public class ReceiveMessages
{


    public static void main(String[] args)
    {


        AmazonSQS sqs = ProxySQSFactory.create(true);

        //create queue
//        try {
//            CreateQueueResult create_result = sqs.createQueue(QUEUE_NAME);
//        } catch (AmazonSQSException e) {
//            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
//                throw e;
//            }
//        }

        String queueUrl = sqs.getQueueUrl(ProxySQSFactory.TEST_QUEUE_NAME).getQueueUrl();
        System.out.println(queueUrl);



        // receive messages from the queue
        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
        for(Message m: messages) {
            System.out.println("Message ID = " + m.getMessageId() + ", Body = " + m.getBody());
        }
//
        // delete messages from the queue
        for (Message m : messages) {
            sqs.deleteMessage(queueUrl, m.getReceiptHandle());
        }
    }
}

