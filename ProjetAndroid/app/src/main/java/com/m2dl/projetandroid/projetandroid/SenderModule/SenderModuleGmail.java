package com.m2dl.projetandroid.projetandroid.SenderModule;

import android.util.Log;

import java.io.File;

/**
 * Created by Assyl on 20/01/2015.
 */
public class SenderModuleGmail implements ISenderModule {

    @Override
    public void sendData(String subject, String userMail, String mailContent, File file) {
        try {
            GMailSender sender = new GMailSender("appbiodiversity@gmail.com", "BioDiver");

            sender.sendMail(subject,
                    mailContent,
                    "appbiodiversity@gmail.com",
                    userMail);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }
}
