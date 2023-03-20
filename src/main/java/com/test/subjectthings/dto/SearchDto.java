package com.test.subjectthings.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {

    private int page;
    private int recordSize;
    private int pageSize;

    private String keyword;
    private String type;

    public SearchDto(){
        this.page =1;
        this.recordSize=10;
        this.pageSize=10;
    }
    //limit 용
    public int getOffSet(){
        return (page -1 ) * recordSize;
    }

}
