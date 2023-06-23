package br.com.trier.springmatutino.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {
    private static final DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static ZonedDateTime strToZonedDateTime(String dateStr) {
    	DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime localDate = LocalDateTime.parse(dateStr, formatacao);
		return ZonedDateTime.of(localDate, ZoneId.systemDefault());
    }

    public static String zonedDateTimeToStr(ZonedDateTime data) {
        return formatacao.format(data);
    }
}

