/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
                    userMail, file);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }

    @Override
    public void sendData(String subject, String userMail, String mailContent) {
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
