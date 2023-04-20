package com.equinix.EmailSender;
import com.google.cloud.bigquery.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Sample to inserting rows into a table without running a load job.
public class BigQueryData
{
    public void insertData(String to, String subject, String timestamp, boolean status)
    {
        // TODO(developer): Replace these variables before running the sample.
        String datasetName = "request_logs";
        String tableName = "request_data";
        // Create a row to insert
        Map<String,Object> rowContent = new HashMap<>();
        rowContent.put("to_address",to);
        rowContent.put("subject",subject);
        rowContent.put("timestamp",timestamp);
        rowContent.put("success_status",status);

        tableInsertRows(datasetName, tableName, rowContent);
    }
    public static void tableInsertRows(
            String datasetName, String tableName, Map<String, Object> rowContent) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.newBuilder()
                    .setProjectId("useful-gearbox-383519")
                    .build()
                    .getService();


            // Get table
            TableId tableId = TableId.of(datasetName, tableName);

            // Inserts rowContent into datasetName:tableId.
            InsertAllResponse response =
                    bigquery.insertAll(
                            InsertAllRequest.newBuilder(tableId)
                                    // More rows can be added in the same RPC by invoking .addRow() on the builder.
                                    // You can also supply optional unique row keys to support de-duplication
                                    // scenarios.
                                    .addRow(rowContent)
                                    .build());

            if (response != null && response.hasErrors()) {
                // If any of the insertions failed, this lets you inspect the errors
                for (Map.Entry<Long, List<BigQueryError>> entry : response.getInsertErrors().entrySet()) {
                    System.out.println("Response error: \n" + entry.getValue());
                }
            } else {
                System.out.println("Rows successfully inserted into table");
            }
        } catch (BigQueryException e) {
            System.out.println("Insert operation not performed \n" + e.toString());
        }
    }
}
