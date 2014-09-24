package it.gruppopam.merchandise.draft.dto;

public class ResultDto {
    String columnName;
    String value;

    public ResultDto(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }
}
