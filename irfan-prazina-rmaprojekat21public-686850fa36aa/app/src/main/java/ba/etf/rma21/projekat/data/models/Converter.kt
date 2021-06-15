package ba.etf.rma21.projekat.data.models

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
@RequiresApi(Build.VERSION_CODES.O)

class Converter {


//    @TypeConverter
//    fun dateToString(date: Date): String {
//        return date.toString()
//    }
//
//    @TypeConverter
//    fun stringToDate(stringDate: String): Date {
//        val dateToConvert = LocalDate.parse(stringDate,DateTimeFormatter.ISO_DATE)
//        return Date.from(dateToConvert.atStartOfDay()
//            .atZone(ZoneId.systemDefault())
//            .toInstant())
//    }

//    @TypeConverter
//    fun localDateTimeToString(dateTime: LocalDateTime): String {
////        var formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd")
////        var dateTimeString = dateTime.format(formatter).toString() + "T"
////        formatter = DateTimeFormatter.ofPattern("hh:mm:ss")
////        dateTimeString+= dateTime.format(formatter).toString()
////        return dateTimeString
//        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).toString()
//    }
//
//    @TypeConverter
//    fun stringToLocalDateTime(dateTimeString: String): LocalDateTime {
//        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
//    }

    @TypeConverter
    fun fromString(lista: List<String>): String {
        var povratni = ""
        for (i in 0 until lista.size) {
            povratni+=lista[i]
            if (i != lista.size-1)
                povratni+=","
        }
        return povratni
    }

    @TypeConverter
    fun fromList(opcije: String): List<String> {
        return opcije.split(",")
    }
}