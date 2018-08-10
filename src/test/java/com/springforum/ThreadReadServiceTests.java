package com.springforum;


import com.springforum.thread.ThreadDAO;
import com.springforum.thread.ThreadReadService;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.thread.event.onThreadView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ThreadReadServiceTests {
    @Mock
    ThreadDAO threadDao;
    @Mock ApplicationEventPublisher publisher;
    ThreadReadService threadReadService;

    @Before
    public void setUp() {
        threadReadService = new ThreadReadService(threadDao, publisher);
    }

    @Test
    public void threadByUserID() {
        when(threadDao.findByUserId(1, null)).thenReturn(List.of(ThreadDTO.builder()
                .id(1)
                .build()));
        List<ThreadDTO> byUserID = threadReadService.getByUserID(1, null);
        assertThat(byUserID).isNotNull();
        assertThat(byUserID.get(0).getId()).isEqualTo(1);
    }

    @Test
    public void threadByID() {
        when(threadDao.findById(1)).thenReturn(ThreadDTO.builder()
                .id(1)
                .build());
        ThreadDTO byID = threadReadService.getThread(1);
        assertThat(byID).isNotNull();
        assertThat(byID.getId()).isEqualTo(1);
        verify(publisher, times(1)).publishEvent(any(onThreadView.class));

    }
}
