package com.efemoney.ussdtoolbox.data.model;

/**
 * Created by Efe on 01/03/2017.
 */

public class InputField extends Field {

    private String label;
    private String hint;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
