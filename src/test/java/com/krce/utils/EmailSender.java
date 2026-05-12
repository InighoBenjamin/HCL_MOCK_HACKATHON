package com.krce.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    private static final String SMTP = "smtp.gmail.com";
    private static final int    PORT = 587;
    private static final String TO   = "shaliniviswanathan42@gmail.com";

    public static void send(int passed, int failed, int skipped,
                            int total, String suiteName) throws Exception {

        String from = System.getenv("MAIL_USERNAME");
        String pass = System.getenv("MAIL_PASSWORD");

        if (from == null || pass == null) {
            System.err.println("EmailSender: MAIL_USERNAME or MAIL_PASSWORD not set.");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",            SMTP);
        props.put("mail.smtp.port",            String.valueOf(PORT));

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        String status  = failed > 0 ? failed + " FAILED" : "ALL PASSED";
        String subject = suiteName + " — " + status + " (" + passed + "/" + total + ")";

        String resultBg    = failed > 0 ? "#FCEBEB" : "#EAF3DE";
        String resultColor = failed > 0 ? "#A32D2D" : "#3B6D11";
        String resultText  = failed > 0
                ? failed + " test(s) FAILED — check the attached report"
                : "All " + total + " tests passed successfully!";

        int passRate = total > 0 ? (passed * 100 / total) : 0;

        String body = "<!DOCTYPE html><html><body style='font-family:system-ui,sans-serif;"
                + "background:#f5f5f3;padding:2rem'>"
                + "<div style='max-width:560px;margin:0 auto;background:#fff;border-radius:12px;"
                + "overflow:hidden;border:1px solid rgba(0,0,0,.1)'>"
                + "<div style='background:#1a1a18;padding:1.25rem 1.5rem'>"
                + "<h2 style='color:#fff;margin:0;font-size:18px;font-weight:500'>BankBot Automation Report</h2>"
                + "<p style='color:#9b9b96;margin:4px 0 0;font-size:12px'>" + suiteName + "</p>"
                + "</div>"
                + "<div style='background:" + resultBg + ";padding:.85rem 1.5rem'>"
                + "<p style='color:" + resultColor + ";margin:0;font-size:13px;font-weight:500'>"
                + resultText + "</p>"
                + "</div>"
                + "<div style='padding:1.25rem 1.5rem'>"
                + "<table style='width:100%;border-collapse:collapse'><tr>"
                + cell("Total",   total,   "#1a1a18")
                + cell("Passed",  passed,  "#3B6D11")
                + cell("Failed",  failed,  failed  > 0 ? "#A32D2D" : "#666")
                + cell("Skipped", skipped, skipped > 0 ? "#854F0B" : "#666")
                + "</tr></table>"
                + "<div style='margin-top:1rem'>"
                + "<p style='font-size:11px;color:#6b6b67;margin:0 0 6px'>Pass rate — "
                + passRate + "% (" + passed + " of " + total + ")</p>"
                + "<div style='background:#f0f0ee;border-radius:99px;height:8px;overflow:hidden'>"
                + "<div style='height:8px;border-radius:99px;background:#639922;width:" + passRate + "%'></div>"
                + "</div></div></div>"
                + "<div style='padding:.85rem 1.5rem;border-top:1px solid rgba(0,0,0,.08)'>"
                + "<p style='font-size:11px;color:#9b9b96;margin:0'>Triggered by GitHub Actions"
                + " &nbsp;·&nbsp; HTML report attached</p>"
                + "</div></div></body></html>";

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(body, "text/html; charset=utf-8");

        Multipart mp = new MimeMultipart();
        mp.addBodyPart(htmlPart);

        java.io.File report =
                new java.io.File("test-output/BankBot_Automation_Suite/BankBot_Automation_Suite.html");
        if (report.exists()) {
            MimeBodyPart attach = new MimeBodyPart();
            attach.attachFile(report);
            mp.addBodyPart(attach);
        }

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from, "GitHub Actions"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO));
        msg.setSubject(subject);
        msg.setContent(mp);

        Transport.send(msg);
        System.out.println("Email sent to: " + TO);
    }

    private static String cell(String label, int value, String color) {
        return "<td style='text-align:center;padding:.5rem'>"
                + "<div style='font-size:24px;font-weight:500;color:" + color + "'>" + value + "</div>"
                + "<div style='font-size:11px;color:#6b6b67;margin-top:3px'>" + label + "</div>"
                + "</td>";
    }
}