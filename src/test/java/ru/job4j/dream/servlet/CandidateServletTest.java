package ru.job4j.dream.servlet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class CandidateServletTest {
    @Test
    public void whenCreateCandidate() throws IOException {
        MemStore store = MemStore.instOf();

        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        PowerMockito.when(req.getParameter("id")).thenReturn("0");
        PowerMockito.when(req.getParameter("name")).thenReturn("n");

        new CandidateServlet().doPost(req, resp);

        Candidate result = store.findAllCandidates().iterator().next();
        Assert.assertThat(result.getName(), is("n"));
    }

    @Test
    public void whenDoGet() throws IOException, ServletException {
        String path = "candidates.jsp";
        String candidatesAttributeKey = "candidates";
        MemStore store = MemStore.instOf();
        store.saveCandidate(new Candidate(0, "n", 0));

        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        PowerMockito.when(req.getRequestDispatcher(path)).thenReturn(dispatcher);

        new CandidateServlet().doGet(req, resp);

        verify(req, times(1)).setAttribute(candidatesAttributeKey, store.findAllCandidates());
        verify(req, times(1)).getRequestDispatcher(path);
        verify(dispatcher, times(1)).forward(req, resp);
    }
}
