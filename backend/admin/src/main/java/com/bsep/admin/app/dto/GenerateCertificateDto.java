package com.bsep.admin.app.dto;


import javax.validation.constraints.NotNull;
import java.util.Date;

// u ovu klasu dodati slanje ekstenzija
public class GenerateCertificateDto {
    @NotNull(message = "End date should not be null or empty")
    private Date startDate;
    @NotNull(message = "End date should not be null or empty")
    private Date endDate;

    public GenerateCertificateDto() {

    }

    public GenerateCertificateDto(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
