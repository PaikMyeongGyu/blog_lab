package com.refactoring.refactoring.board.common;

import java.util.List;
import java.util.function.Function;

/**
 * 공통된 요소로 List<T> 형태로 무언가가 계속 들어오지 않을까???
 * -> 이걸 기준으로 하면 리팩토링이 가능할 것 같음.
 *
 * 내부에서 값 추출하는 건 T -> R로 바꿔주는 Function 인터페이스를 사용하자.
 */
public record PageResponse<T, R>(
        Boolean hasNext,
        Integer size,
        List<T> data,
        R lastId
) {
    public PageResponse(List<T> data, Function<T, R> extractIdFunction, PageSize pageSize) {
        this(
                data.size() > pageSize.getSize(),
                Math.min(data.size(), pageSize.getSize()),
                List.copyOf(data.subList(0, Math.min(data.size(), pageSize.getSize()))),
                data.isEmpty() ? null : extractIdFunction.apply(data.get(Math.min(data.size(), pageSize.getSize()) - 1))
        );
    }
}
