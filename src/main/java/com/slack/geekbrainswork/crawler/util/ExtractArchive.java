package com.slack.geekbrainswork.crawler.util;

import org.rauschig.jarchivelib.Compressor;
import org.rauschig.jarchivelib.CompressorFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Nikolay on 13.11.2016.
 */
public class ExtractArchive {

    // качаем файл с помощью Stream
    public static void download(String urlStr, String archiveName) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fos = new FileOutputStream(archiveName);

        int count=0;
        while((count = bis.available()) != -1)
        {
            fos.write(count);
        }

        fos.close();
        bis.close();
    }

    public void extract(String archiveName, String destinationName) throws IOException {
        File archive = new File(archiveName);
        File destination = new File(destinationName);

        Compressor compressor = CompressorFactory.createCompressor(archive);
        compressor.decompress(archive, destination);


    }

}
