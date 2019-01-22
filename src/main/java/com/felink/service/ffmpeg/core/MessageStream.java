package com.felink.service.ffmpeg.core;

import com.felink.service.common.utility.FileUtil;
import com.felink.service.configurer.Path;
import org.apache.log4j.Logger;

class MessageStream extends Thread
{
    private java.io.InputStream inputStream;
    MessageStream(java.io.InputStream is)
    {
        inputStream = is;
    }

    public void run()
    {   char[] letters = new char[200];
        try
        {
            FileUtil.fromStreamSave(inputStream, Path.LOG_PATH + "\\log.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
