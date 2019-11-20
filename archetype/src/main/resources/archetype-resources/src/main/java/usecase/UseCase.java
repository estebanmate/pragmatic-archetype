#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.usecase;

@FunctionalInterface
public interface UseCase<T extends Request, U extends Response> {
    Void nullRequest = null;
    Void nullResponse = null;

    U execute(final T request);
}