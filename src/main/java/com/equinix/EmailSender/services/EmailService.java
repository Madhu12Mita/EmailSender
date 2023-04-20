package com.equinix.EmailSender.services;

import com.equinix.EmailSender.EmailRequest;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private final SendGrid sendGrid = new SendGrid("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");

    public Response sendEmail(EmailRequest emailRequest) throws IOException {
        Email from = new Email("madhumita.koralla@gmail.com");

        Content content = new Content("text/plain", emailRequest.getBody());

        Mail mail = new Mail(from, emailRequest.getSubject(), new Email(emailRequest.getTo()), content);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

            return response;
        } catch (IOException e) {
            throw e;
        }
    }
}
