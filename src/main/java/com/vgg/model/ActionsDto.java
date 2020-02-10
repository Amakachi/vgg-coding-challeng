package com.vgg.model;

import javax.validation.constraints.NotBlank;

public class ActionsDto {

    private Integer id;
    @NotBlank
    private String note;
    @NotBlank
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
