package com.example.spring.common.jpa.vo;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.spring.common.enums.IEnum;
import com.example.spring.common.exception.OperationNotSupportedException;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Getter
@Setter
@ToString
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaPage {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private Integer pageSize;
    private Integer pageNum;
    private List<JpaSort> sorts;

    protected JpaPage(Integer pageSize, Integer pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public static JpaPage of(Integer pageSize, Integer pageNum) {
        return new JpaPage(pageSize, pageNum);
    }

    public JpaPage addSort(JpaSort jpaSort) {
        if (sorts == null) {
            sorts = new LinkedList<>();
        }
        sorts.add(jpaSort);
        return this;
    }

    public static Sort buildSort(MyDirection myDirection, String sortName) {
        Sort.Direction direction = (myDirection == MyDirection.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort.Order order = new Sort.Order(direction, sortName);
        return Sort.by(order);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class JpaSort {

        private String name;
        // d a
        private String direction;

        protected JpaSort(String name, String direction) {
            this.name = name;
            this.direction = direction;
        }

        public static JpaSort of(String name, MyDirection direction) {
            return new JpaSort(name, direction.getName());
        }
    }

    public enum MyDirection implements IEnum<MyDirection, Integer> {
        /**
         * desc
         */
        DESC("d"),
        /**
         * asc
         */
        ASC("a");

        private String name;

        MyDirection(String name) {
            this.name = name;
        }

        @Override
        public Integer getCode() {
            throw new OperationNotSupportedException();
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    public Pageable buildPageable() {
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        pageNum = (pageNum == null || pageNum < 1) ? 1 : pageNum;

        Sort sort = null;
        if (sorts != null && !sorts.isEmpty()) {
            List<Sort.Order> orders = new LinkedList<>();
            for (JpaSort s : sorts) {
                Sort.Direction direction = s.getDirection().equalsIgnoreCase(MyDirection.ASC.getName())
                    ? Sort.Direction.ASC : Sort.Direction.DESC;
                orders.add(new Sort.Order(direction, s.getName()));
            }
            sort = Sort.by(orders);
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        log.info(">>>>>pageable:{}", pageable);
        return pageable;
    }
}
