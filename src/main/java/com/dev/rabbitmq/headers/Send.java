package com.mmr.rabbitmq.headers;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
 
 
public class Send {
 
	private static final String EXCHANGE_NAME = "test_exchange_headers";
 
	public static void main(String[] argv)  throws IOException, TimeoutException{
			Connection connection = ConnectionUtils.getConnection();
			Channel channel = connection.createChannel();
 
			//声明路由名字和类型
			channel.exchangeDeclare(EXCHANGE_NAME, "headers", false, true, null);
			
			//设置消息头键值对信息
			Map<String, Object> headers = new Hashtable<String, Object>();
			headers.put("auth", "123456");
			headers.put("key", 12345);
			Builder builder = new Builder();
			builder.headers(headers);
			
			String message="hello headers";
			channel.basicPublish(EXCHANGE_NAME, "", builder.build(), message.getBytes());
			System.out.println("Sent msg is '" + message + "'");
 
			channel.close();
			connection.close();
			
		}
}