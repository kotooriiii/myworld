package com.github.kotooriiii.myworld.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ImageUtils
{
    public record ImageFile (byte[] data, String fileExtension)
    {};

    public static ImageFile createImageFile(String imageUrl)
    {
        HttpResponse<InputStream> response;
        try (HttpClient client = HttpClient.newHttpClient())
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        } catch (IOException e)
        {
            throw new RuntimeException(e);

        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        try (InputStream inputStream = response.body();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return new ImageFile(byteArrayOutputStream.toByteArray(), response.headers().firstValue("content-type").orElseThrow());
        } catch (NoSuchElementException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
