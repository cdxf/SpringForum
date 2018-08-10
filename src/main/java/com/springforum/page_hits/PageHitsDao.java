package com.springforum.page_hits;

import com.springforum.Sequences;
import com.springforum.Tables;
import com.springforum.tables.PageHits;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

@Service
@Transactional
@Slf4j
public class PageHitsDao {
    @Autowired DSLContext context;
    PageHits pageHits = Tables.PAGE_HITS;

    @Async
    public void increasePageHit(String ip) {
        var sequence = Sequences.SEQUENCE;
        var now = currentTimeMillis();
        Field<Long> nextval = sequence.nextval();
        var next = context.select(nextval).fetchOne(nextval);
        context.insertInto(pageHits, pageHits.ID, pageHits.CREATED_TIME, pageHits.IP)
                .values(next.intValue(), Timestamp.from(Instant.now()), ip).execute();
        out.printf("increasePageHit in %s ns \n", currentTimeMillis() - now);
        out.println("increasePageHit Thread: " + Thread.currentThread().getName());
    }

    @Transactional(readOnly = true)
    public int getUsersOnline() {
        var time1 = currentTimeMillis();
        var now = Timestamp.from(Instant.now().minusSeconds(30));
        var query = context.selectDistinct(pageHits.IP)
                .from(pageHits)
                .where(pageHits.CREATED_TIME.ge(now));
        out.println("Get Users Online SQL " + query.getSQL());
        int execute = query.execute();
        log.info(String.format("Users: %s in %s ns", execute, currentTimeMillis() - time1));
        return execute;
    }
}
