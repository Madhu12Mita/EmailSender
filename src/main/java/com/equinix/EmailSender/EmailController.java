package com.equinix.EmailSender;
import java.sql.Timestamp;
import java.time.Instant;

import com.equinix.EmailSender.services.EmailService;
import com.sendgrid.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;
    private Object Log;
//    public String getJson()
//    {
//        //EmailRequest emailRequest=new EmailRequest();
//        Map<String, Object> map = new HashMap<>();
//        map.put("to_address", emailRequest.getTo());
//        map.put("subject", emailRequest.getSubject());
//        map.put("body", emailRequest.getBody());
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = null;
//        try
//        {
//            jsonString = mapper.writeValueAsString(map);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return jsonString;
//    }

    public String getToAddress(@RequestBody EmailRequest emailRequest) throws IOException
    {
        String to_address=emailRequest.getTo();
        System.out.println(String.valueOf(to_address));
        return to_address;
    }
    public String getSubject(@RequestBody EmailRequest emailRequest)
    {
        String subject= emailRequest.getSubject();
        return subject;
    }
    @PostMapping("/sendemail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) throws IOException {
        Response response = emailService.sendEmail(emailRequest);
        if(response.getStatusCode() == 200 || response.getStatusCode() == 202)
        {
            Instant instant = Instant.now();
            Timestamp timestamp = Timestamp.from(instant);
            //String json_data=this.getJson();
            BigQueryData big=new BigQueryData();
            big.insertData(emailRequest.getTo(), emailRequest.getSubject(), String.valueOf(timestamp), true);
            return new ResponseEntity<>("Sent Succesfully", HttpStatus.OK);
        }
        else
        {
            Instant instant = Instant.now();
            Timestamp timestamp = Timestamp.from(instant);
            //String json_data=this.getJson();
            BigQueryData big=new BigQueryData();
            //big.insertData(json_data,timestamp.toString(), false);
            return new ResponseEntity<>("Failed to Send", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
