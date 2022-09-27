package com.example.bookservice.configurations;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoConfiguration {
    @Value("${amazon.aws.access_key}")
    private String accessKey;
    @Value("${amazon.aws.secret_key}")
    private String secretKey;
    @Value("${amazon.aws.region}")
    private String region;

    private AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    private AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(awsCredentials());
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard().withCredentials(awsCredentialsProvider()).withRegion(region).build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB());
    }
}
