package com.aviationeventsservice.service.impl;

import com.aviationeventsservice.service.AviationEventFetcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AviationEventFetcherServiceImpl implements AviationEventFetcherService {

    @Value("${skytamer.url:https://www.skytamer.com/}")
    private String url;

    @Override
    public String fetchAviationEvents(String date) {

        String urlPath = url + date + ".html";
        try {

            StringBuilder sb = new StringBuilder();
            Document doc = Jsoup.connect(urlPath).get();
            Element eventsRow = doc.select("td[colspan=5] font").first();

            if (eventsRow != null) {
                Elements events = eventsRow.select("p");

                for (Element event : events) {
                    sb.append(event.text()).append(" ");
                }
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not retrieve On this day info for: " + date;
        }
    }

    @Override
    public String convertDateToDDMM(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMdd");

        try {
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
