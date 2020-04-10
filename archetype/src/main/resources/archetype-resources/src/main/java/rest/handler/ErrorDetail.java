#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class ErrorDetail {

    private Integer status;
    private String title;
    private String detail;
    private final List<String> errors = new ArrayList<>();

    public static ErrorDetail with() {
        return new ErrorDetail();
    }

    public ErrorDetail status(Integer status ) {
        this.status = status;
        return this;
    }

    public ErrorDetail title(String title) {
        this.title = title;
        return this;
    }

    public ErrorDetail detail(String detail) {
        this.detail = detail;
        return this;
    }

    public ErrorDetail objectErrors(List<ObjectError> errors) {
        if (errors == null || errors.isEmpty()) {
            return this;
        }
        errors.forEach(error -> this.errors.add(error.getObjectName() + " : " + error.getDefaultMessage()));
        return this;
    }

    public ErrorDetail fieldErrors(List<FieldError> errors) {
        if (errors == null || errors.isEmpty()) {
            return this;
        }
        errors.forEach(error -> this.errors.add(error.getField() + " : " + error.getDefaultMessage()));
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public List<String> getErrors() {
        return errors;
    }
}
