#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.usecase;

@FunctionalInterface
public interface UseCase {
    Void nullRequest = null;
    Void nullResponse = null;

    Response execute(final Request request);
}
