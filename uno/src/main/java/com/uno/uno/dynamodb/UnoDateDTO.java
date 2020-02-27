package com.uno.uno.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "UnoDateRequests")
public class UnoDateDTO {
    private String id;
    private String fromDate;
    private String toDate;
    private Integer difference;


    public UnoDateDTO(String id, String fromDate, String toDate, Integer difference) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.difference = difference;
    }

    @DynamoDBHashKey
    public String getId() {
        return id;
    }

	public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    @DynamoDBAttribute
    public String getToDate() {
        return this.toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @DynamoDBAttribute
    public Integer getDifference() {
        return this.difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

}