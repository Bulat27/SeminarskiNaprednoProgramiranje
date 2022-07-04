package rs.ac.bg.fon.nprog.client.jsonreport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Repair;
import rs.ac.bg.fon.nprog.commonlibrary.domain.ServiceBook;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonReport {

    public static void generateReport(ServiceBook serviceBook){
        List<Repair> repairs = serviceBook.getRepairs();
        List<Repair> thisYearsRepairs = getThisYearsRepairs(repairs);

        JsonObject jsonReport = new JsonObject();

        JsonArray repairsJsonArray = new JsonArray();
        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Repair r: thisYearsRepairs) {
            repairsJsonArray.add(r.getName());
            totalRevenue = totalRevenue.add(r.getTotalRevenue());
            totalExpense = totalExpense.add(r.getTotalExpense());
        }

        jsonReport.addProperty("vlasnik", serviceBook.toString());
        jsonReport.addProperty("opisVozila", serviceBook.getVehicleDescription());
        jsonReport.addProperty("godina", String.valueOf(LocalDate.now().getYear()));
        jsonReport.add("servisi", repairsJsonArray);
        jsonReport.addProperty("ukupnaDobit", totalRevenue);
        jsonReport.addProperty("ukupniTroskovi", totalExpense);

        writeToAFile(jsonReport);
    }

    private static List<Repair> getThisYearsRepairs(List<Repair> repairs) {
        int currentYear = LocalDate.now().getYear();
        List<Repair> thisYearsRepairs = new ArrayList<>();

        for (Repair r: repairs){
            if(r.getStartDate().getYear() == currentYear){
                thisYearsRepairs.add(r);
            }
        }
        return thisYearsRepairs;
    }

    private static void writeToAFile(JsonObject jsonReport) {
        try (FileWriter out = new FileWriter("izvestaj.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            out.write(gson.toJson(jsonReport));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
