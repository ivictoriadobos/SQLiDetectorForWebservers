package core.interfaces;

public interface ICommandInQueryParamsOrHeadersFilter {

    boolean doesContainCommandInQueryParamsOrHeaders(ILogPoint aLogPoint);
}
