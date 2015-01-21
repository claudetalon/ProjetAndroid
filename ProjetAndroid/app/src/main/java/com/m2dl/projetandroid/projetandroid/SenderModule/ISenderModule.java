package com.m2dl.projetandroid.projetandroid.SenderModule;

import java.io.File;

/**
 * Created by Assyl on 20/01/2015.
 */
public interface ISenderModule  {

    void sendData(String subject, String userMail, String mailContent,  File file);
    void sendData(String subject, String userMail, String mailContent);
}
