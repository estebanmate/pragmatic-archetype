#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.handler;


import java.util.List;

import javax.annotation.Priority;

import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Priority(value = Ordered.HIGHEST_PRECEDENCE)
public final class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
                                                                         final HttpHeaders headers,
                                                                         final HttpStatus status,
                                                                         final WebRequest request) {
        var detail = String.format("HTTP Method '%s' not supported for this endpoint", ex.getMethod());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ErrorDetail.with()
                        .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .title(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                        .detail(detail));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ErrorDetail.with()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .detail("invalid request body")
                        .objectErrors(globalErrors)
                        .fieldErrors(fieldErrors));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                                                                     final HttpHeaders headers,
                                                                     final HttpStatus status,
                                                                     final WebRequest request) {
        var detail = String.format("media type '%s' not supported", ex.getContentType());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ErrorDetail.with()
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .title(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
                        .detail(detail));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(final HttpMediaTypeNotAcceptableException ex,
                                                                      final HttpHeaders headers,
                                                                      final HttpStatus status,
                                                                      final WebRequest request) {
        var detail = "invalid Accept header value";
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ErrorDetail.with()
                        .status(HttpStatus.NOT_ACCEPTABLE.value())
                        .title(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase())
                        .detail(detail));
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(final MissingPathVariableException ex,
                                                               final HttpHeaders headers,
                                                               final HttpStatus status,
                                                               final WebRequest request) {
        var detail = String.format("missing path variable '%s'", ex.getVariableName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ErrorDetail.with()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .detail(detail));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
                                                                          final HttpHeaders headers,
                                                                          final HttpStatus status,
                                                                          final WebRequest request) {
        var detail = String.format("missing request param '%s' of type '%s'", ex.getParameterName(), ex.getParameterType());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ErrorDetail.with()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .detail(detail));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                   final HttpHeaders headers,
                                                                   final HttpStatus status,
                                                                   final WebRequest request) {
        var detail = String.format("unknown resource endpoint '%s'", ex.getRequestURL());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ErrorDetail.with()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .detail(detail));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex,
                                                             final Object body,
                                                             final HttpHeaders headers,
                                                             final HttpStatus status,
                                                             final WebRequest request) {
        var detail = String.format("internal error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(ErrorDetail.with()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .detail(detail));
    }
}