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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AviationEventFetcherServiceImpl implements AviationEventFetcherService {

    @Value("${skytamer.url:https://www.skytamer.com/}")
    private String url;

    private static final List<String> MONTHS = Arrays.asList(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    );

    @Override
    public String fetchAviationEvents(String date) {
        String urlPath = url + date + ".html";
        StringBuilder events = new StringBuilder();

        try {
            Document doc = Jsoup.connect(urlPath).get();
            Elements eventRows = doc.select("td[colspan=5]");

            for (Element row : eventRows) {
                if (containsMonth(row.text())) {
                    events.append(row.text()).append(" ");
                }
            }
            return events.toString().trim();
        } catch (IOException e) {
            System.err.println("Error fetching aviation events for date: " + date);
            e.printStackTrace();
            return "Could not retrieve On this day info for: " + date;
        }
    }

    /**
     * Date needs to be in MMdd format for skytamer.com URL
     * @param dateStr
     * @return String
     */
    @Override
    public String convertDateToDDMM(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMdd");

        try {
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "0000";
        }
    }

    /**
     * Since the HTML structure can be different, this method will go through every row and look for desired month
     * and the content will be retrieved successfully.
     * @param text
     * @return boolean
     */
    private boolean containsMonth(String text) {
        return MONTHS.stream().anyMatch(text::contains);
    }

}
