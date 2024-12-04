package model.builder;

import model.Report;

import java.time.LocalDate;

public class ReportBuilder {

    private Report report;

    public ReportBuilder()
    {
        report=new Report();
    }
    public ReportBuilder setId(Long id){
        report.setUserId(id);
        return this;
    }

    public ReportBuilder setUsername(String username){
        report.setUsername(username);
        return this;
    }
    public ReportBuilder setStock(int stock)
    {
        report.setStock(stock);
        return this;
    }
    public ReportBuilder setPrice(int price)
    {
        report.setPrice(price);
        return this;
    }
    public Report build()
    {
        return report;
    }
}
