package com.springforum.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jooq.Condition;
import org.jooq.SortField;
import org.jooq.TableField;
import org.jooq.impl.DSL;

import java.sql.Timestamp;

public interface TimestampKeyset {
    static TimestampKeyset getDefault() {
        return new TimestampKeyset() {
            @Override
            public Long getTimestamp() {
                return null;

            }

            @Override
            public Boolean getDesc() {
                return true;
            }
        };
    }

    Long getTimestamp();

    Boolean getDesc();

    default CondtionAndOrder seek(TableField<?, Timestamp> field) {
        var desc = getDesc() == null ? true : getDesc();
        var timestamp = getTimestamp();
        Condition condition = DSL.noCondition();
        if (timestamp != null) {
            condition = desc ? field.le(new Timestamp(getTimestamp()))
                    : field.ge(new Timestamp(getTimestamp()));
        }
        var order = desc ? field.desc() : field.asc();
        return new CondtionAndOrder(condition, order);
    }

    @Data
    @AllArgsConstructor
    class CondtionAndOrder {
        public Condition condition;
        public SortField<Timestamp> order;
    }
}
