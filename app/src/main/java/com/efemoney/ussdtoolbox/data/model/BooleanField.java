package com.efemoney.ussdtoolbox.data.model;

/**
 * Created by Efe on 01/03/2017.
 */

public class BooleanField extends Field {

    private String label;
    private String templateYes;
    private String templateNo;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTemplateYes() {
        return templateYes;
    }

    public void setTemplateYes(String templateYes) {
        this.templateYes = templateYes;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }
}
